package intern.rikkei.warehousesystem.repository;

import intern.rikkei.warehousesystem.dto.response.InboundStatisticsResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InboundRepository extends JpaRepository<Inbound, Long>, JpaSpecificationExecutor<Inbound> {
    @Query(value = """
            SELECT new intern.rikkei.warehousesystem.dto.response.InboundStatisticsResponse(
                i.productType,
                i.supplierCd,
                SUM(i.quantity),
                COUNT(i.id)
            )
            FROM Inbound i
            WHERE (:productType IS NULL OR i.productType = :productType)
              AND (:supplierCd IS NULL OR i.supplierCd = :supplierCd)
            GROUP BY i.productType, i.supplierCd
            """,
            countQuery = """
            SELECT COUNT(DISTINCT CONCAT(i.productType, i.supplierCd))
            FROM Inbound i
            WHERE (:productType IS NULL OR i.productType = :productType)
              AND (:supplierCd IS NULL OR i.supplierCd = :supplierCd)
            """)
    Page<InboundStatisticsResponse> findInboundStatistics(
            @Param("productType") ProductType productType,
            @Param("supplierCd") SupplierCd supplierCd,
            Pageable pageable
    );
}
