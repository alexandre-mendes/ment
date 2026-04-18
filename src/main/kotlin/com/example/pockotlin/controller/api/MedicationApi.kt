package com.example.pockotlin.controller.api

import com.example.pockotlin.model.dto.MedicationLotRequest
import com.example.pockotlin.model.dto.MedicationLotResponse
import com.example.pockotlin.model.dto.MedicationPriceRequest
import com.example.pockotlin.model.dto.MedicationPriceResponse
import com.example.pockotlin.model.dto.MedicationRequest
import com.example.pockotlin.model.dto.MedicationResponse
import com.example.pockotlin.model.entity.MedicationCategory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.util.UUID

@Tag(name = "Medications", description = "Endpoints para gerenciamento de medicamentos")
interface MedicationApi {

    @Operation(summary = "Cadastrar Medicamento", description = "Cria um novo registro de medicamento")
    fun create(@RequestBody medicationRequest: MedicationRequest): ResponseEntity<MedicationResponse>

    @Operation(summary = "Consultar medicamentos", description = "Lista medicamentos, incluindo a quantidade disponível e o preço atual, com filtros por nome e categoria")
    fun findByCriteria(
        @RequestParam searchByName: String,
        @RequestParam(required = false) category: MedicationCategory?
    ): ResponseEntity<List<MedicationResponse>>

    @Operation(summary = "Adicionar lote de estoque", description = "Adiciona um novo lote de um medicamento ao estoque")
    fun addLot(
        @PathVariable id: UUID,
        @RequestBody request: MedicationLotRequest
    ): ResponseEntity<MedicationLotResponse>

    @Operation(summary = "Adicionar preço de medicamento", description = "Adiciona um novo registro de preço para um medicamento, mantendo o histórico")
    fun addPrice(
        @PathVariable id: UUID,
        @RequestBody request: MedicationPriceRequest
    ): ResponseEntity<MedicationPriceResponse>
}
