package com.example.pockotlin.service

import com.example.pockotlin.model.dto.MedicationLotRequest
import com.example.pockotlin.model.dto.MedicationLotResponse
import com.example.pockotlin.model.entity.MedicationLotEntity
import com.example.pockotlin.repository.MedicationLotRepository
import com.example.pockotlin.repository.MedicationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException

@Service
class MedicationLotService(
    private val medicationLotRepository: MedicationLotRepository,
    private val medicationRepository: MedicationRepository
) {

    @Transactional
    fun create(request: MedicationLotRequest): MedicationLotResponse {
        val medication = medicationRepository.findById(request.medicationId)
            .orElseThrow { NoSuchElementException("Medication with id ${request.medicationId} not found") }

        val newLot = MedicationLotEntity(
            medication = medication,
            quantity = request.quantity,
            purchasePrice = request.purchasePrice,
            expirationDate = request.expirationDate
        )

        val savedLot = medicationLotRepository.save(newLot)
        return toResponse(savedLot)
    }

    private fun toResponse(entity: MedicationLotEntity): MedicationLotResponse {
        return MedicationLotResponse(
            id = entity.id!!,
            medicationId = entity.medication.id!!,
            quantity = entity.quantity,
            purchasePrice = entity.purchasePrice,
            entryDate = entity.entryDate,
            expirationDate = entity.expirationDate
        )
    }
}
