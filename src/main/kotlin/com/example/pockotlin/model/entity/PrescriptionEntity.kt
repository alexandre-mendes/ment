import com.example.pockotlin.model.entity.converter.MapConverter
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "prescriptions")
data class PrescriptionEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    val patientId: String,
    val doctorId: String,

    @Enumerated(EnumType.STRING)
    val sector: PrescriptionSector,

    @Enumerated(EnumType.STRING)
    var status: PrescriptionStatus = PrescriptionStatus.PENDING,

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapConverter::class)
    val metadata: Map<String, Any>?,

    @OneToMany(mappedBy = "prescription", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<PrescriptionItemEntity> = mutableListOf()
)
