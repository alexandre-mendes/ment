package com.example.pockotlin.repository

import com.example.pockotlin.model.entity.MedicationPriceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface MedicationPriceRepository : JpaRepository<MedicationPriceEntity, UUID> {
    fun findByMedicationIdAndIsActiveTrue(medicationId: UUID): Optional<MedicationPriceEntity>
    fun findTopByMedicationIdOrderByActivatedAtDesc(medicationId: UUID): MedicationPriceEntity?
}
