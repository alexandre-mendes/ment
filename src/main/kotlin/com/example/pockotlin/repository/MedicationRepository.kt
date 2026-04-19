package com.example.pockotlin.repository

import com.example.pockotlin.model.entity.MedicationCategory
import com.example.pockotlin.model.entity.MedicationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface MedicationRepository : JpaRepository<MedicationEntity, UUID> {
    fun findByNameContainingIgnoreCase(name: String): List<MedicationEntity>
    fun findByNameContainingIgnoreCaseAndCategory(name: String, category: MedicationCategory): List<MedicationEntity>
    fun existsByNameIgnoreCase(name: String): Boolean
}
