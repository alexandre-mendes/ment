package com.example.pockotlin.model.entity.converter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class MapConverter(private val objectMapper: ObjectMapper) : AttributeConverter<Map<String, Any>?, String?> {

    override fun convertToDatabaseColumn(attribute: Map<String, Any>?): String? {
        return if (attribute == null) {
            null
        } else {
            try {
                objectMapper.writeValueAsString(attribute)
            } catch (e: Exception) {
                // Consider logging the exception
                throw RuntimeException("Error converting map to JSON", e)
            }
        }
    }

    override fun convertToEntityAttribute(dbData: String?): Map<String, Any>? {
        return if (dbData.isNullOrBlank()) {
            null
        } else {
            try {
                objectMapper.readValue(dbData, object : TypeReference<Map<String, Any>>() {})
            } catch (e: Exception) {
                // Consider logging the exception
                throw RuntimeException("Error converting JSON to map", e)
            }
        }
    }
}
