package intern.rikkei.warehousesystem.repository;

import intern.rikkei.warehousesystem.entity.Inbound;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundRepository extends JpaRepository<Inbound, Long> {
    boolean existsByInvoice(String invoice);
}
