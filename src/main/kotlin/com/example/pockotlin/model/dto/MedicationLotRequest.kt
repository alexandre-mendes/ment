package com.example.pockotlin.model.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class MedicationLotRequest(
    @Schema(description = "ID do medicamento ao qual o lote pertence", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
    val medicationId: UUID,
    @Schema(description = "Quantidade de itens no lote", example = "50")
    val quantity: Int,
    @Schema(description = "Preço de compra por unidade no lote", example = "5.25")
    val purchasePrice: BigDecimal,
    @Schema(description = "Data de validade do lote", example = "2025-12-31")
    val expirationDate: LocalDate? = null
)
