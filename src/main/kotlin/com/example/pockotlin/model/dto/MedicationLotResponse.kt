package com.example.pockotlin.model.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class MedicationLotResponse(
    @Schema(description = "ID do lote", example = "c2eebc99-9c0b-4ef8-bb6d-6bb9bd380a33")
    val id: UUID,
    @Schema(description = "ID do medicamento", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
    val medicationId: UUID,
    @Schema(description = "Quantidade de itens no lote", example = "50")
    val quantity: Int,
    @Schema(description = "Preço de compra por unidade", example = "5.25")
    val purchasePrice: BigDecimal,
    @Schema(description = "Data de entrada do lote no sistema", example = "2023-10-27")
    val entryDate: LocalDate,
    @Schema(description = "Data de validade do lote", example = "2025-12-31")
    val expirationDate: LocalDate?
)
