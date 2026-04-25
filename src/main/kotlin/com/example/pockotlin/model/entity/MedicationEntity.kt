package com.example.pockotlin.model.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "medications")
data class MedicationEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    val name: String,
    @Enumerated(EnumType.STRING)
    val category: MedicationCategory
) {
    constructor() : this(null, "", MedicationCategory.OTHERS)
}
