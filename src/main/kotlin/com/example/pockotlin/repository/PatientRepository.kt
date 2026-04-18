package com.example.pockotlin.repository

import com.example.pockotlin.model.entity.PatientEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PatientRepository : JpaRepository<PatientEntity, Long>
