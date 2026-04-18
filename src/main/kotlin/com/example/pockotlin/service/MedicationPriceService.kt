package com.example.pockotlin.service

import com.example.pockotlin.model.dto.MedicationPriceRequest
import com.example.pockotlin.model.dto.MedicationPriceResponse
import com.example.pockotlin.model.entity.MedicationPriceEntity
import com.example.pockotlin.repository.MedicationPriceRepository
import com.example.pockotlin.repository.MedicationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.NoSuchElementException
import java.util.UUID

@Service
class MedicationPriceService(
    private val medicationPriceRepository: MedicationPriceRepository,
    private val medicationRepository: MedicationRepository
) {

    @Transactional
    fun create(medicationId: UUID, request: MedicationPriceRequest): MedicationPriceResponse {
        val medication = medicationRepository.findById(medicationId)
            .orElseThrow { NoSuchElementException("Medication with id $medicationId not found") }

        medicationPriceRepository.findByMedicationIdAndIsActiveTrue(medicationId).ifPresent {
            it.isActive = false
            it.inactivatedAt = LocalDateTime.now()
            medicationPriceRepository.save(it)
        }

        val newPrice = MedicationPriceEntity(
            medication = medication,
            price = request.price
        )

        val savedPrice = medicationPriceRepository.save(newPrice)
        return toResponse(savedPrice)
    }

    private fun toResponse(entity: MedicationPriceEntity): MedicationPriceResponse {
        return MedicationPriceResponse(
            id = entity.id!!,
            medicationId = entity.medication.id!!,
            price = entity.price,
            isActive = entity.isActive,
            activatedAt = entity.activatedAt
        )
    }
}
