package com.example.pockotlin.controller.api

import com.example.pockotlin.model.dto.MedicationPriceRequest
import com.example.pockotlin.model.dto.MedicationPriceResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Medication Prices", description = "Endpoints para gerenciamento de preços de medicamentos")
interface MedicationPriceApi {

    @Operation(summary = "Cadastrar novo preço de medicamento", description = "Cadastro de novo preço para um medicamento. O sistema inativa o preço anterior automaticamente.")
    fun create(@RequestBody request: MedicationPriceRequest): ResponseEntity<MedicationPriceResponse>
}
