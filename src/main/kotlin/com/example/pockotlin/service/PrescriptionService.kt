package com.example.pockotlin.service

import com.example.pockotlin.config.PrescriptionEventPublisher
import com.example.pockotlin.model.dto.PrescriptionRequest
import com.example.pockotlin.model.dto.PrescriptionResponse
import com.example.pockotlin.model.dto.PrescriptionStatusChangeRequest
import com.example.pockotlin.model.entity.DispensedLotEntity
import com.example.pockotlin.model.entity.PrescriptionEntity
import com.example.pockotlin.model.entity.PrescriptionItemEntity
import com.example.pockotlin.model.entity.PrescriptionStatus
import com.example.pockotlin.repository.MedicationLotRepository
import com.example.pockotlin.repository.MedicationPriceRepository
import com.example.pockotlin.repository.PrescriptionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException
import java.util.UUID

@Service
class PrescriptionService(
    private val prescriptionRepository: PrescriptionRepository,
    private val eventPublisher: PrescriptionEventPublisher,
    private val medicationLotRepository: MedicationLotRepository,
    private val medicationPriceRepository: MedicationPriceRepository
) {

    @Transactional()
    fun create(request: PrescriptionRequest): PrescriptionResponse {
        val prescriptionEntity = PrescriptionEntity(
            patientId = request.patientId,
            doctorId = request.doctorId,
            sector = request.sector,
            metadata = request.metadata
        )

        val items = request.medications.map {
            PrescriptionItemEntity(
                medicationId = it.medicationId,
                quantity = it.quantity,
                observation = it.observation,
                prescription = prescriptionEntity
            )
        }

        prescriptionEntity.items.addAll(items)
        val savedPrescription = prescriptionRepository.save(prescriptionEntity)
        return toResponse(savedPrescription)
    }

    @Transactional
    fun updateStatus(id: UUID, request: PrescriptionStatusChangeRequest): PrescriptionResponse {
        if (request.status == PrescriptionStatus.PENDING) {
            throw IllegalArgumentException("Cannot change status to PENDING via this endpoint.")
        }

        val prescription = prescriptionRepository.findById(id)
            .orElseThrow { NoSuchElementException("Prescription with id $id not found") }

        when {
            prescription.status == PrescriptionStatus.AUTHORIZED && request.status == PrescriptionStatus.CANCELED -> {
                throw IllegalArgumentException("Cannot cancel a prescription that has already been authorized.")
            }
            prescription.status == PrescriptionStatus.CANCELED && request.status == PrescriptionStatus.AUTHORIZED -> {
                throw IllegalArgumentException("Cannot authorize a prescription that has already been canceled.")
            }
            request.status == PrescriptionStatus.AUTHORIZED -> {
                decreaseStock(prescription)
            }
        }

        prescription.status = request.status
        val updatedPrescription = prescriptionRepository.save(prescription)
        val response = toResponse(updatedPrescription)

        when (response.status) {
            PrescriptionStatus.AUTHORIZED -> eventPublisher.publishAuthorized(response)
            PrescriptionStatus.CANCELED -> eventPublisher.publishCanceled(response)
            PrescriptionStatus.PENDING -> println("")
        }

        return response
    }

    private fun decreaseStock(prescription: PrescriptionEntity) {
        prescription.items
            .map { item ->
                val lots = medicationLotRepository.findAvailableByMedicationIdOrderByExpirationDateAsc(item.medicationId)
                val totalAvailable = lots.sumOf { it.quantity }
                if (totalAvailable < item.quantity) {
                    throw IllegalStateException("Not enough stock for medication ${item.medicationId}. Required: ${item.quantity}, Available: $totalAvailable")
                }

                var remaining = item.quantity
                val currentPrice = medicationPriceRepository.findTopByMedicationIdOrderByActivatedAtDesc(item.medicationId)
                    ?: throw IllegalStateException("Medication ${item.medicationId} has no price registered.")
                item.saleUnitPrice = currentPrice.price

                lots.filter { remaining > 0 }
                    .map { lot ->
                        val quantityToTake = minOf(remaining, lot.quantity)

                        DispensedLotEntity(
                            prescriptionItem = item,
                            medicationLot = lot,
                            quantity = quantityToTake
                        ).also { item.dispensedLots.add(it) }

                        lot.quantity -= quantityToTake
                        remaining -= quantityToTake
                        medicationLotRepository.save(lot)
                    }
            }
    }

    private fun toResponse(entity: PrescriptionEntity): PrescriptionResponse {
        return PrescriptionResponse(
            id = entity.id!!,
            patientId = entity.patientId,
            doctorId = entity.doctorId,
            sector = entity.sector,
            status = entity.status,
            metadata = entity.metadata,
            medications = entity.items.map {
                com.example.pockotlin.model.dto.PrescriptionMedication(
                    medicationId = it.medicationId,
                    quantity = it.quantity,
                    observation = it.observation
                )
            }
        )
    }
}
