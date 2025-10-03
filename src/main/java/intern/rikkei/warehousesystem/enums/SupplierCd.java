package intern.rikkei.warehousesystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum SupplierCd {
    VIETNAM("VN"),
    THAILAND("TH"),
    MALAYSIA("MY"),
    INDONESIA("ID"),
    SINGAPORE("SG"),
    PHILIPPINES("PH"),
    LAOS("LA"),
    MYANMAR("MM"),
    TIMOR_LESTE("TL");

    private final String code;

    public static SupplierCd fromCode(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(intern.rikkei.warehousesystem.enums.SupplierCd.values())
                .filter(supplier -> supplier.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid supplier code: " + code));
    }
}
