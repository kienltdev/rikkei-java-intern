package intern.rikkei.warehousesystem.repository;

import intern.rikkei.warehousesystem.dto.inbound.response.InboundStatisticsResponse;
import intern.rikkei.warehousesystem.dto.inventory.response.InventoryDetailResponse;
import intern.rikkei.warehousesystem.dto.report.response.MonthlyQuantity;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InboundRepository extends JpaRepository<Inbound, Long>, JpaSpecificationExecutor<Inbound> {

    /**
     * Finds an Inbound entity by its ID and applies a pessimistic write lock.
     * This method should be used within a transaction when the entity is going to be updated
     * to prevent race conditions.
     * @param id The ID of the Inbound entity.
     * @return an Optional containing the locked Inbound entity if found.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM Inbound i WHERE i.id = :id")
    Optional<Inbound> findByIdAndLock(@Param("id") Long id);

    @Query(value = """
            SELECT new intern.rikkei.warehousesystem.dto.inbound.response.InboundStatisticsResponse(
                i.productType,
                i.supplierCd,
                COALESCE(SUM(i.quantity), 0) ,
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

    @Query("""
            SELECT COALESCE(SUM(i.quantity), 0L) 
                        FROM Inbound i
                                    WHERE (:productType IS NULL OR i.productType = :productType)
                                                AND (:supplierCd IS NULL OR i.supplierCd = :supplierCd)
                                                            AND (:invoice IS NULL OR i.invoice = :invoice)
            """)
    Long sumQuantityByFilters(
            @Param("productType") ProductType productType,
            @Param("supplierCd") SupplierCd supplierCd,
            @Param("invoice") String invoice
    );

    @Query(
            value = """
                SELECT new intern.rikkei.warehousesystem.dto.inventory.response.InventoryDetailResponse(
                    i.id,
                    i.invoice,
                    i.productType,
                    i.supplierCd,
                    i.quantity,
                    (i.quantity - COALESCE(SUM(o.quantity), 0L)) AS availableQuantity,
                    i.status
                )
                FROM Inbound i
                LEFT JOIN Outbound o ON o.inbound.id = i.id
                WHERE (:inbId IS NULL OR i.id = :inbId)
                AND (:invoice IS NULL OR i.invoice LIKE %:invoice%)
                AND (:productType IS NULL OR i.productType = :productType)
                AND (:supplierCd IS NULL OR i.supplierCd = :supplierCd)
                GROUP BY i.id, i.invoice, i.productType, i.supplierCd, i.quantity, i.status
    
                """,
            countQuery = """
                SELECT COUNT(i)
                FROM Inbound i
                WHERE (:inbId IS NULL OR i.id = :inbId)
                AND (:invoice IS NULL OR i.invoice LIKE %:invoice%)
                AND (:productType IS NULL OR i.productType = :productType)
                AND (:supplierCd IS NULL OR i.supplierCd = :supplierCd)
                """
    )
    Page<InventoryDetailResponse> findInventoryDetails(
            @Param("inbId") Long inbId,
            @Param("invoice") String invoice,
            @Param("productType") ProductType productType,
            @Param("supplierCd") SupplierCd supplierCd,
            Pageable pageable

    );

    @Query("SELECT COALESCE(SUM(i.quantity), 0L) FROM Inbound i WHERE i.receiveDate < :date")
    Long sumQuantityByReceiveDateBefore(@Param("date") LocalDate date);

//    @Query("SELECT COALESCE(SUM(i.quantity), 0L) FROM Inbound i WHERE i.receiveDate >= :startDate AND i.receiveDate <= :endDate")
//    Long sumQuantityByReceiveDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    @Query("""
            SELECT new intern.rikkei.warehousesystem.dto.report.response.MonthlyQuantity(MONTH(i.receiveDate), SUM(i.quantity))
            FROM Inbound i
            WHERE YEAR(i.receiveDate) = :year
            GROUP BY MONTH(i.receiveDate)
""")
    List<MonthlyQuantity> findMonthlySummariesByYear(@Param("year") int year);

}
