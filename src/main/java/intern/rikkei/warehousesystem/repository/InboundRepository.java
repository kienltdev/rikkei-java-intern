package intern.rikkei.warehousesystem.repository;

import intern.rikkei.warehousesystem.entity.Inbound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InboundRepository extends JpaRepository<Inbound, Long>, JpaSpecificationExecutor<Inbound> {
}
