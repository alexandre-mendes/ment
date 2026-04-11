package com.example.pockotlin.listener

import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class MedicalRequestListener {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @RabbitListener(queues = ["request_medical"])
    fun listen(message: String) {
        logger.info("Received message from request_medical queue: {}", message)
        // TODO: Implement business logic to process the message
    }
}
