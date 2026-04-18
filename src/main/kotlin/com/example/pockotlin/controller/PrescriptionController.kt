package com.example.pockotlin.controller

import com.example.pockotlin.controller.api.PrescriptionApi
import com.example.pockotlin.model.dto.PrescriptionRequest
import com.example.pockotlin.model.dto.PrescriptionResponse
import com.example.pockotlin.model.dto.PrescriptionStatusChangeRequest
import com.example.pockotlin.service.PrescriptionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class PrescriptionController(private val prescriptionService: PrescriptionService) : PrescriptionApi {

    override fun updateStatus(id: UUID, request: PrescriptionStatusChangeRequest): ResponseEntity<PrescriptionResponse> {
        val response = prescriptionService.updateStatus(id, request)
        return ResponseEntity.ok(response)
    }
}
