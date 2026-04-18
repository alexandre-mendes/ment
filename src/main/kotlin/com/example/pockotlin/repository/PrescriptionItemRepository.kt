package com.example.pockotlin.repository

import com.example.pockotlin.model.entity.PrescriptionItemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PrescriptionItemRepository : JpaRepository<PrescriptionItemEntity, UUID>
