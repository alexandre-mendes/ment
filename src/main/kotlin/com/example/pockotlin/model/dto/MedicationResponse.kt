package com.example.pockotlin.model.dto

import com.example.pockotlin.model.entity.MedicationCategory
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.util.UUID

data class MedicationResponse(
    @get:Schema(description = "ID único do medicamento", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
    val id: UUID,
    @get:Schema(description = "Nome do medicamento", example = "Paracetamol 500mg")
    val name: String,
    @get:Schema(description = "Categoria do medicamento", example = "ANALGESICS_AND_ANTIPYRETICS")
    val category: MedicationCategory,
    @get:Schema(description = "Preço de venda atual", example = "12.99")
    val salesPrice: BigDecimal?,
    @get:Schema(description = "Quantidade total em estoque", example = "150")
    val quantity: Long
)
