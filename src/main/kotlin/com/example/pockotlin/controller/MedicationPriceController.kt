package com.example.pockotlin.controller

import com.example.pockotlin.controller.api.MedicationPriceApi
import com.example.pockotlin.model.dto.MedicationPriceRequest
import com.example.pockotlin.model.dto.MedicationPriceResponse
import com.example.pockotlin.service.MedicationPriceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/medication-prices")
class MedicationPriceController(
    private val medicationPriceService: MedicationPriceService
) : MedicationPriceApi {

    @PostMapping
    override fun create(@RequestBody request: MedicationPriceRequest): ResponseEntity<MedicationPriceResponse> {
        val response = medicationPriceService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}
