package intern.rikkei.warehousesystem.entity;

import intern.rikkei.warehousesystem.entity.audit.AuditableEntity;
import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "inbound")
@Getter
@Setter
public class Inbound extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice", length = 9, nullable = false)
    private String invoice;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", length = 50, nullable = false)
    private ProductType productType;

    @Column(name = "supplier_cd", length = 2, nullable = false)
    private SupplierCode supplierCd;

    @Column(name = "receive_date")
    private Instant receiveDate;

    @Column(name = "status", nullable = false)
    private InboundStatus status;

    @Column(name = "quantity")
    private Integer quantity;
}
