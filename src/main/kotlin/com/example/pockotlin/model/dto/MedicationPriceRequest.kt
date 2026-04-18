package com.example.pockotlin.model.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.util.UUID

data class MedicationPriceRequest(
    @get:Schema(description = "ID do medicamento ao qual o preço se refere", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
    val medicationId: UUID,
    @get:Schema(description = "Novo preço de venda", example = "12.99")
    val price: BigDecimal
)
