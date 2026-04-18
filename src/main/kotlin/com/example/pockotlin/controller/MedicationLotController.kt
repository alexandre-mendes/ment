package com.example.pockotlin.controller

import com.example.pockotlin.controller.api.MedicationLotApi
import com.example.pockotlin.model.dto.MedicationLotRequest
import com.example.pockotlin.model.dto.MedicationLotResponse
import com.example.pockotlin.service.MedicationLotService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/medication-lots")
class MedicationLotController(
    private val medicationLotService: MedicationLotService
) : MedicationLotApi {

    @PostMapping
    override fun create(@RequestBody request: MedicationLotRequest): ResponseEntity<MedicationLotResponse> {
        val response = medicationLotService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}
