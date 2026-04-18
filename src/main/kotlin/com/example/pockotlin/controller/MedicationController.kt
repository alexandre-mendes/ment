package com.example.pockotlin.controller

import com.example.pockotlin.controller.api.MedicationApi
import com.example.pockotlin.model.dto.MedicationLotRequest
import com.example.pockotlin.model.dto.MedicationLotResponse
import com.example.pockotlin.model.dto.MedicationPriceRequest
import com.example.pockotlin.model.dto.MedicationPriceResponse
import com.example.pockotlin.model.dto.MedicationRequest
import com.example.pockotlin.model.dto.MedicationResponse
import com.example.pockotlin.model.entity.MedicationCategory
import com.example.pockotlin.service.MedicationLotService
import com.example.pockotlin.service.MedicationPriceService
import com.example.pockotlin.service.MedicationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/api/v1/medications")
class MedicationController(
    private val medicationService: MedicationService,
    private val medicationLotService: MedicationLotService,
    private val medicationPriceService: MedicationPriceService
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

    @PatchMapping("/{id}/lot")
    override fun addLot(
        @PathVariable id: UUID,
        @RequestBody request: MedicationLotRequest
    ): ResponseEntity<MedicationLotResponse> {
        val response = medicationLotService.create(id, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @PatchMapping("/{id}/price")
    override fun addPrice(
        @PathVariable id: UUID,
        @RequestBody request: MedicationPriceRequest
    ): ResponseEntity<MedicationPriceResponse> {
        val response = medicationPriceService.create(id, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }
}
