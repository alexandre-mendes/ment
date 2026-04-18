package com.example.pockotlin.controller

import com.example.pockotlin.model.dto.PatientDTO
import com.example.pockotlin.service.PatientService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/patients")
class PatientController(private val patientService: PatientService) {

    @GetMapping
    fun getAllPatients(): List<PatientDTO> {
        return patientService.getAllPatients()
    }
}
