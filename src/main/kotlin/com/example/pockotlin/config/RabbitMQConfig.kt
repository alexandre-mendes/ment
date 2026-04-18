package com.example.pockotlin.config

import org.springframework.amqp.core.Queue
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Bean
    fun requestMedicalQueue(): Queue {
        return Queue("request_medical", true) // true for durable
    }

    @Bean
    fun authorizedRequestMedicalQueue(): Queue {
        return Queue("authorized_request_medical", true)
    }

    @Bean
    fun canceledRequestMedicalQueue(): Queue {
        return Queue("canceled_request_medical", true)
    }

    @Bean
    fun messageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }
}
