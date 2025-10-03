package intern.rikkei.warehousesystem.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ProductType {
    AIRCON("Aircon"),
    SPARE_PART("Spare_part");

    private final String code;

    @JsonValue
    public String getCode() {
        return code;
    }

    public static ProductType fromCode(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(ProductType.values())
                .filter(type -> type.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid product type code: " + code));
    }
}
