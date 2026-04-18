package com.example.pockotlin.controller

import com.example.pockotlin.controller.api.MedicationApi
import com.example.pockotlin.model.dto.MedicationRequest
import com.example.pockotlin.model.dto.MedicationResponse
import com.example.pockotlin.model.entity.MedicationCategory
import com.example.pockotlin.service.MedicationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/medications")
class MedicationController(
    private val medicationService: MedicationService
) : MedicationApi {

    @PostMapping
    override fun create(@RequestBody medicationRequest: MedicationRequest): ResponseEntity<MedicationResponse> {
        val medicationResponse = medicationService.create(medicationRequest)
        return ResponseEntity.status(HttpStatus.CREATED).body(medicationResponse)
    }

    @GetMapping
    override fun findByCriteria(
        @RequestParam searchByName: String,
        @RequestParam(required = false) category: MedicationCategory?
    ): ResponseEntity<List<MedicationResponse>> {
        val medications = medicationService.findByCriteria(searchByName, category)
        return ResponseEntity.ok(medications)
    }
}
