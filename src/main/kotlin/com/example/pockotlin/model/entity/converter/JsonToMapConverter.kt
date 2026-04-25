package com.example.pockotlin.model.entity.converter

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class JsonToMapConverter(private val objectMapper: ObjectMapper) : AttributeConverter<Map<String, Any>, String> {

    override fun convertToDatabaseColumn(attribute: Map<String, Any>?): String? {
        return attribute?.let { objectMapper.writeValueAsString(it) }
    }

    override fun convertToEntityAttribute(dbData: String?): Map<String, Any>? {
        return dbData?.let { objectMapper.readValue(it, object : TypeReference<Map<String, Any>>() {}) }
    }
}
