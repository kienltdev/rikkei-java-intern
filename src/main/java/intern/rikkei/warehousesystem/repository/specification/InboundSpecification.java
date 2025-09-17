package intern.rikkei.warehousesystem.repository.specification;

import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.entity.Inbound_;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCode;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class InboundSpecification {

    private InboundSpecification() {}

    public static Specification<Inbound> filterBy(String productType, String supplierCd) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(productType)) {
                try {
                    ProductType type = ProductType.valueOf(productType.toUpperCase());
                    predicates.add(criteriaBuilder.equal(root.get(Inbound_.productType), type));
                } catch (IllegalArgumentException e) {
                    // Bỏ qua nếu productType không hợp lệ hoặc có thể throw exception tùy nghiệp vụ
                }
            }

            if (StringUtils.hasText(supplierCd)) {
                try {
                    SupplierCode code = SupplierCode.fromCode(supplierCd.toUpperCase());
                    predicates.add(criteriaBuilder.equal(root.get(Inbound_.supplierCd), code));
                } catch (IllegalArgumentException e) {
                    // Bỏ qua nếu supplierCd không hợp lệ
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}