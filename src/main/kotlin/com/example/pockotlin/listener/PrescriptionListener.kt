package com.example.pockotlin.listener

import com.example.pockotlin.model.dto.PrescriptionRequest
import com.example.pockotlin.service.PrescriptionService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class PrescriptionListener(private val prescriptionService: PrescriptionService) {

    @RabbitListener(queues = ["request_medical"])
    fun receivePrescriptionRequest(request: PrescriptionRequest) {
        println("Received prescription request: $request")
        prescriptionService.create(request)
    }
}
