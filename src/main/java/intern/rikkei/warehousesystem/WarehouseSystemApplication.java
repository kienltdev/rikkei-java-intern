package intern.rikkei.warehousesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WarehouseSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseSystemApplication.class, args);
    }

}
