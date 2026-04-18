package com.example.pockotlin.repository

import com.example.pockotlin.model.entity.PrescriptionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PrescriptionRepository : JpaRepository<PrescriptionEntity, UUID>
