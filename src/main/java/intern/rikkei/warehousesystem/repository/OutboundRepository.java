package intern.rikkei.warehousesystem.repository;

import intern.rikkei.warehousesystem.entity.Outbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OutboundRepository extends JpaRepository<Outbound,Long>, JpaSpecificationExecutor<Outbound> {

}
