package com.example.pockotlin.controller.api

import com.example.pockotlin.model.dto.PrescriptionRequest
import com.example.pockotlin.model.dto.PrescriptionResponse
import com.example.pockotlin.model.dto.PrescriptionStatusChangeRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Tag(name = "Prescriptions", description = "Operações relacionadas a prescrições médicas")
@RequestMapping("/api/v1/prescriptions")
interface PrescriptionApi {

    @Operation(summary = "Mudar o status de uma prescrição")
    @PatchMapping("/{id}")
    fun updateStatus(@PathVariable id: UUID, @RequestBody request: PrescriptionStatusChangeRequest): ResponseEntity<PrescriptionResponse>
}
