package intern.rikkei.warehousesystem.repository;

import intern.rikkei.warehousesystem.entity.Outbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OutboundRepository extends JpaRepository<Outbound, Long>, JpaSpecificationExecutor<Outbound> {
    @Query("SELECT COALESCE(SUM(o.quantity), 0) FROM Outbound o WHERE o.inbound.id = :inboundId")
    Integer sumQuantityByInboundId(@Param("inboundId") Long inboundId);
    List<Outbound> findByInboundId(Long inboundId);
}
