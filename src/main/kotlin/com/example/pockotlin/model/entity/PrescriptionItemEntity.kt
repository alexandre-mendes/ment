package com.example.pockotlin.model.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "prescription_items")
data class PrescriptionItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_id", nullable = false)
    var prescription: PrescriptionEntity? = null,

    val medicationId: UUID,
    val quantity: Int,
    val observation: String?,

    @Column(name = "sale_unit_price")
    var saleUnitPrice: BigDecimal? = null,

    @OneToMany(mappedBy = "prescriptionItem", cascade = [CascadeType.ALL], orphanRemoval = true)
    val dispensedLots: MutableList<DispensedLotEntity> = mutableListOf()
) {
    constructor() : this(null, null, UUID.randomUUID(), 0, null, null, mutableListOf())
}
