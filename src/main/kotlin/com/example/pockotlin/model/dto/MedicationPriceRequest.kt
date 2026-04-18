package com.example.pockotlin.model.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.util.UUID

data class MedicationPriceRequest(
    @get:Schema(description = "Novo preço de venda", example = "12.99")
    val price: BigDecimal
)
