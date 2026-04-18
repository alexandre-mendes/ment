package com.example.pockotlin.model.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "medication_lots")
data class MedicationLotEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    val medication: MedicationEntity,

    @Column(nullable = false)
    var quantity: Int,

    @Column(nullable = false)
    val purchasePrice: BigDecimal,

    @Column(nullable = false)
    val entryDate: LocalDate = LocalDate.now(),

    val expirationDate: LocalDate? = null
)
