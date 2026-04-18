package com.example.pockotlin.controller.api

import com.example.pockotlin.model.dto.MedicationRequest
import com.example.pockotlin.model.dto.MedicationResponse
import com.example.pockotlin.model.entity.MedicationCategory
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Medications", description = "Endpoints para gerenciamento de medicamentos")
interface MedicationApi {

    @Operation(summary = "Cadastrar Medicamento", description = "Cria um novo registro de medicamento")
    fun create(@RequestBody medicationRequest: MedicationRequest): ResponseEntity<MedicationResponse>

    @Operation(summary = "Consultar medicamentos", description = "Lista medicamentos, incluindo a quantidade disponível e o preço atual, com filtros por nome e categoria")
    fun findByCriteria(
        @RequestParam searchByName: String,
        @RequestParam(required = false) category: MedicationCategory?
    ): ResponseEntity<List<MedicationResponse>>
}
