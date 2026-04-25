package com.example.pockotlin.model.entity

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "dispensed_lots")
data class DispensedLotEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prescription_item_id", nullable = false)
    val prescriptionItem: PrescriptionItemEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_lot_id", nullable = false)
    val medicationLot: MedicationLotEntity,

    @Column(nullable = false)
    val quantity: Int
) {
    constructor() : this(null, PrescriptionItemEntity(), MedicationLotEntity(), 0)
}
