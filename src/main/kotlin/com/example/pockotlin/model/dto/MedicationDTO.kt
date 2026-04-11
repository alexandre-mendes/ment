package com.example.pockotlin.model.dto

data class MedicationDTO(
    val id: Long,
    val nome: String,
    val descricao: String,
    val estoque: Int
)
