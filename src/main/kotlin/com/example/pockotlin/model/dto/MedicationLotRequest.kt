package com.example.pockotlin.model.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class MedicationLotRequest(
    @get:Schema(description = "Quantidade de itens no lote", example = "50")
    val quantity: Int,
    @get:Schema(description = "Preço de compra por unidade no lote", example = "5.25")
    val purchasePrice: BigDecimal,
    @get:Schema(description = "Data de validade do lote", example = "2025-12-31")
    val expirationDate: LocalDate? = null
)
