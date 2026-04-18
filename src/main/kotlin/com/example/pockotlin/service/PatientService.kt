package com.example.pockotlin.service

import com.example.pockotlin.model.dto.PatientDTO
import com.example.pockotlin.repository.PatientRepository
import org.springframework.stereotype.Service

@Service
class PatientService(private val patientRepository: PatientRepository) {

    fun getAllPatients(): List<PatientDTO> {
        return patientRepository.findAll().map {
            PatientDTO(
                id = it.id,
                name = it.name,
                email = it.email
            )
        }
    }
}
