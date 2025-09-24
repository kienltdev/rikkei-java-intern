package intern.rikkei.warehousesystem.entity;

import intern.rikkei.warehousesystem.entity.audit.AuditableEntity;
import intern.rikkei.warehousesystem.enums.ShippingMethod;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "outbound")
public class Outbound extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inb_id", nullable = false)
    private Inbound inbound;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "shipping_method", length = 1)
    private ShippingMethod shippingMethod;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

}
