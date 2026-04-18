package com.example.pockotlin.repository

import com.example.pockotlin.model.entity.MedicationLotEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MedicationLotRepository : JpaRepository<MedicationLotEntity, UUID> {
    @Query("SELECT COALESCE(SUM(m.quantity), 0) FROM MedicationLotEntity m WHERE m.medication.id = :medicationId")
    fun sumQuantityByMedicationId(medicationId: UUID): Long
}
