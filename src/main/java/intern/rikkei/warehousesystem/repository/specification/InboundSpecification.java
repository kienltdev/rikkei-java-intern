package intern.rikkei.warehousesystem.repository.specification;

import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.entity.Inbound_;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class InboundSpecification {

    private InboundSpecification() {}

    public static Specification<Inbound> filterBy(ProductType productType, SupplierCd supplierCd, String invoice) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (productType != null) {
                predicates.add(criteriaBuilder.equal(root.get(Inbound_.productType), productType));
            }

            if (supplierCd != null) {
                predicates.add(criteriaBuilder.equal(root.get(Inbound_.supplierCd), supplierCd));
            }

            if (StringUtils.hasText(invoice)) {
                predicates.add(criteriaBuilder.like(root.get(Inbound_.invoice), "%" + invoice + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}