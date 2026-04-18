package com.example.pockotlin.model.dto

import com.example.pockotlin.model.entity.PrescriptionStatus
import io.swagger.v3.oas.annotations.media.Schema

data class PrescriptionStatusChangeRequest(
    @get:Schema(description = "Novo status para a prescrição", example = "AUTHORIZED")
    val status: PrescriptionStatus
)
