package com.example.pockotlin.config

import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Bean
    fun requestMedicalQueue(): Queue {
        return Queue("request_medical", true) // true for durable
    }
}
