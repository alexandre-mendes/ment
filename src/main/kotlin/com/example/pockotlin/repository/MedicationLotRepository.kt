package com.example.pockotlin.repository

import com.example.pockotlin.model.entity.MedicationLotEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MedicationLotRepository : JpaRepository<MedicationLotEntity, UUID> {
    @Query("SELECT COALESCE(SUM(m.quantity), 0) FROM MedicationLotEntity m WHERE m.medication.id = :medicationId")
    fun sumQuantityByMedicationId(medicationId: UUID): Long

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT m FROM MedicationLotEntity m WHERE m.medication.id = :medicationId AND m.quantity > 0 ORDER BY m.expirationDate ASC")
    fun findAvailableByMedicationIdOrderByExpirationDateAsc(medicationId: UUID): List<MedicationLotEntity>
}
