package com.example.pockotlin.client

import com.example.pockotlin.model.dto.MedicationDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class PharmacyClient(
    private val restTemplate: RestTemplate,
    @Value("\${pharmacy.api.url}") private val apiUrl: String
) {

    fun getMedications(): List<MedicationDTO>? {
        val response = restTemplate.exchange(
            "$apiUrl/medications",
            HttpMethod.GET,
            null,
            object : ParameterizedTypeReference<List<MedicationDTO>>() {}
        )
        return response.body
    }
}
