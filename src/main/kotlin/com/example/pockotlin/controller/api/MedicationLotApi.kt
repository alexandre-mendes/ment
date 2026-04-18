package com.example.pockotlin.controller.api

import com.example.pockotlin.model.dto.MedicationLotRequest
import com.example.pockotlin.model.dto.MedicationLotResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Medication Lots", description = "Endpoints para gerenciamento de lotes e estoque de medicamentos")
interface MedicationLotApi {

    @Operation(summary = "Adicionar lote de medicamento", description = "Registra a entrada de um novo lote de um medicamento no estoque, com quantidade e preço de compra.")
    fun create(@RequestBody request: MedicationLotRequest): ResponseEntity<MedicationLotResponse>
}
