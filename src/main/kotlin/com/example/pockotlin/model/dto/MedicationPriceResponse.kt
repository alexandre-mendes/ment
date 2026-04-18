package com.example.pockotlin.model.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class MedicationPriceResponse(
    @Schema(description = "ID do registro de preço", example = "b1eebc99-9c0b-4ef8-bb6d-6bb9bd380a22")
    val id: UUID,
    @Schema(description = "ID do medicamento", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
    val medicationId: UUID,
    @Schema(description = "Preço de venda", example = "12.99")
    val price: BigDecimal,
    @Schema(description = "Indica se este é o preço ativo", example = "true")
    val isActive: Boolean,
    @Schema(description = "Data e hora em que o preço foi ativado", example = "2023-10-27T10:00:00")
    val activatedAt: LocalDateTime
)
