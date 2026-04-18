package com.example.pockotlin.service

import com.example.pockotlin.model.dto.MedicationRequest
import com.example.pockotlin.model.dto.MedicationResponse
import com.example.pockotlin.model.entity.MedicationCategory
import com.example.pockotlin.model.entity.MedicationEntity
import com.example.pockotlin.repository.MedicationLotRepository
import com.example.pockotlin.repository.MedicationPriceRepository
import com.example.pockotlin.repository.MedicationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class MedicationService(
    private val medicationRepository: MedicationRepository,
    private val medicationPriceRepository: MedicationPriceRepository,
    private val medicationLotRepository: MedicationLotRepository
) {

    fun create(medicationRequest: MedicationRequest): MedicationResponse {
        val medicationEntity = MedicationEntity(
            name = medicationRequest.name,
            category = medicationRequest.category
        )
        val savedMedication = medicationRepository.save(medicationEntity)
        return toResponse(savedMedication)
    }

    fun findByCriteria(name: String, category: MedicationCategory?): List<MedicationResponse> {
        val medications = if (category == null) {
            medicationRepository.findByNameContainingIgnoreCase(name)
        } else {
            medicationRepository.findByNameContainingIgnoreCaseAndCategory(name, category)
        }
        return medications.map { toResponse(it) }
    }

    private fun toResponse(medicationEntity: MedicationEntity): MedicationResponse {
        val medicationId = medicationEntity.id!!
        val activePrice = medicationPriceRepository.findByMedicationIdAndIsActiveTrue(medicationId)
        val totalQuantity = medicationLotRepository.sumQuantityByMedicationId(medicationId)

        return MedicationResponse(
            id = medicationId,
            name = medicationEntity.name,
            category = medicationEntity.category,
            salesPrice = activePrice.map { it.price }.orElse(null),
            quantity = totalQuantity
        )
    }
}
