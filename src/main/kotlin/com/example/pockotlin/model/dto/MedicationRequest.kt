package com.example.pockotlin.model.dto

import com.example.pockotlin.model.entity.MedicationCategory
import io.swagger.v3.oas.annotations.media.Schema

data class MedicationRequest(
    @Schema(description = "Nome do medicamento", example = "Paracetamol 500mg")
    val name: String,
    @Schema(description = "Categoria do medicamento")
    val category: MedicationCategory
)
