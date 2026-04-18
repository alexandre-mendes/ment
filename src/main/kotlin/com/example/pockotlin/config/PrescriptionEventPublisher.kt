package com.example.pockotlin.config

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class PrescriptionEventPublisher(private val rabbitTemplate: RabbitTemplate) {

    fun publishAuthorized(prescription: Any) {
        rabbitTemplate.convertAndSend("authorized_request_medical", prescription)
    }

    fun publishCanceled(prescription: Any) {
        rabbitTemplate.convertAndSend("canceled_request_medical", prescription)
    }
}
