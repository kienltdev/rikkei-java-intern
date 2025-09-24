package intern.rikkei.warehousesystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum SupplierCd {
    VN("VN", "Việt Nam"),
    TH("TH", "Thái Lan"),
    MY("MY", "Malaysia"),
    ID("ID", "Indonesia"),
    SG("SG", "Singapore"),
    PH("PH", "Philippines"),
    LA("LA", "Lào"),
    MM("MM", "Myanmar"),
    TL("TL", "Đông Timor");

    private final String code;
    private final String name;

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
