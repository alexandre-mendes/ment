package com.example.pockotlin.model.dto

import com.example.pockotlin.model.entity.PrescriptionSector
import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

data class PrescriptionRequest(
    @get:Schema(description = "ID do paciente", example = "p123")
    val patientId: String,
    @get:Schema(description = "ID do médico", example = "d456")
    val doctorId: String,
    @get:Schema(description = "Setor da prescrição", example = "EMERGENCY")
    val sector: PrescriptionSector,
    @get:Schema(description = "Lista de medicamentos da prescrição")
    val medications: List<PrescriptionMedication>,
    @get:Schema(description = "Campo livre para metadados em formato JSON", example = "{\"source\":\"mobile-app\"}")
    val metadata: Map<String, Any>?
)

data class PrescriptionMedication(
    @get:Schema(description = "ID do medicamento", example = "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11")
    val medicationId: UUID,
    @get:Schema(description = "Quantidade solicitada", example = "2")
    val quantity: Int,
    @get:Schema(description = "Observação sobre o item", example = "Tomar 1 a cada 8 horas")
    val observation: String?
)
