package intern.rikkei.warehousesystem.repository;

import intern.rikkei.warehousesystem.entity.Outbound;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutboundRepository extends JpaRepository<Outbound, Long>, JpaSpecificationExecutor<Outbound> {
    @Query("SELECT COALESCE(SUM(o.quantity), 0) FROM Outbound o WHERE o.inbound.id = :inboundId")
    Integer sumQuantityByInboundId(@Param("inboundId") Long inboundId);
    List<Outbound> findByInboundId(Long inboundId);

    @Query("""
        SELECT COALESCE(SUM(o.quantity), 0L)
        FROM Outbound o
        WHERE o.inbound.id IN (
            SELECT i.id
            FROM Inbound i
            WHERE (:productType IS NULL OR i.productType = :productType)
            AND (:supplierCd IS NULL OR i.supplierCd = :supplierCd)
            AND (:invoice IS NULL OR i.invoice = :invoice)
        )
""")
    Long sumQuantityByInboundFilter(
            @Param("productType") ProductType productType,
            @Param("supplierCd") SupplierCd supplierCd,
            @Param("invoice") String invoice
    );
}
