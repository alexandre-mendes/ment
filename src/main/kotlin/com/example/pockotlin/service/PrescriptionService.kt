package com.example.pockotlin.service

import com.example.pockotlin.config.PrescriptionEventPublisher
import com.example.pockotlin.model.dto.PrescriptionRequest
import com.example.pockotlin.model.dto.PrescriptionResponse
import com.example.pockotlin.model.dto.PrescriptionStatusChangeRequest
import com.example.pockotlin.model.entity.PrescriptionEntity
import com.example.pockotlin.model.entity.PrescriptionItemEntity
import com.example.pockotlin.model.entity.PrescriptionStatus
import com.example.pockotlin.repository.MedicationLotRepository
import com.example.pockotlin.repository.PrescriptionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException
import java.util.UUID

@Service
class PrescriptionService(
    private val prescriptionRepository: PrescriptionRepository,
    private val eventPublisher: PrescriptionEventPublisher,
    private val medicationLotRepository: MedicationLotRepository
) {

    @Transactional
    fun create(request: PrescriptionRequest): PrescriptionResponse {
        val prescriptionEntity = PrescriptionEntity(
            patientId = request.patientId,
            doctorId = request.doctorId,
            sector = request.sector,
            metadata = request.metadata
        )

        val items = request.medications.map {
            val item = PrescriptionItemEntity(
                medicationId = it.medicationId,
                quantity = it.quantity,
                observation = it.observation
            )
            item.prescription = prescriptionEntity
            item
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

        if (prescription.status == PrescriptionStatus.AUTHORIZED && request.status == PrescriptionStatus.CANCELED) {
            throw IllegalArgumentException("Cannot cancel a prescription that has already been authorized.")
        }

        if (prescription.status == PrescriptionStatus.CANCELED && request.status == PrescriptionStatus.AUTHORIZED) {
            throw IllegalArgumentException("Cannot authorize a prescription that has already been canceled.")
        }

        if (request.status == PrescriptionStatus.AUTHORIZED) {
            decreaseStock(prescription.items)
        }

        prescription.status = request.status
        val updatedPrescription = prescriptionRepository.save(prescription)
        val response = toResponse(updatedPrescription)

        when (response.status) {
            PrescriptionStatus.AUTHORIZED -> eventPublisher.publishAuthorized(response)
            PrescriptionStatus.CANCELED -> eventPublisher.publishCanceled(response)
            else -> {} // Do nothing for PENDING or other statuses
        }

        return response
    }

    private fun decreaseStock(items: List<PrescriptionItemEntity>) {
        for (item in items) {
            var remainingQuantityToDispense = item.quantity
            val lots = medicationLotRepository.findAvailableByMedicationIdOrderByExpirationDateAsc(item.medicationId)

            val totalAvailable = lots.sumOf { it.quantity }
            if (totalAvailable < remainingQuantityToDispense) {
                throw IllegalStateException("Not enough stock for medication ${item.medicationId}. Required: $remainingQuantityToDispense, Available: $totalAvailable")
            }

            for (lot in lots) {
                if (remainingQuantityToDispense <= 0) break

                val quantityToTake = minOf(remainingQuantityToDispense, lot.quantity)
                lot.quantity -= quantityToTake
                remainingQuantityToDispense -= quantityToTake
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
