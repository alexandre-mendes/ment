package com.example.pockotlin.model.dto

import com.example.pockotlin.model.entity.PrescriptionSector
import com.example.pockotlin.model.entity.PrescriptionStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class PrescriptionResponse(
    @get:Schema(description = "ID da prescrição", example = "e2eebc99-9c0b-4ef8-bb6d-6bb9bd380a44")
    val id: UUID,
    @get:Schema(description = "ID do paciente", example = "p123")
    val patientId: String,
    @get:Schema(description = "ID do médico", example = "d456")
    val doctorId: String,
    @get:Schema(description = "Setor da prescrição", example = "EMERGENCY")
    val sector: PrescriptionSector,
    @get:Schema(description = "Status atual da prescrição", example = "PENDING")
    val status: PrescriptionStatus,
    @get:Schema(description = "Campo livre para metadados em formato JSON", example = "{\"source\":\"mobile-app\"}")
    val metadata: String?,
    @get:Schema(description = "Lista de medicamentos da prescrição")
    val medications: List<PrescriptionMedication>
)
