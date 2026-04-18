package com.example.pockotlin.model.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "medication_prices")
data class MedicationPriceEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    val medication: MedicationEntity,

    @Column(nullable = false)
    val price: BigDecimal,

    @Column(nullable = false)
    var isActive: Boolean = true,

    @Column(nullable = false)
    val activatedAt: LocalDateTime = LocalDateTime.now(),

    var inactivatedAt: LocalDateTime? = null
)
