package intern.rikkei.warehousesystem.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ShippingMethod {
    AIR("A"),
    SEA("S"),
    TRAIL("T"),
    ROAD("R");

    private final String code;

    @JsonValue
    public String getCode(){
        return code;
    }

    public static ShippingMethod fromCode(String code){
        if(code == null){
            return null;
        }

        return Stream.of(ShippingMethod.values())
                .filter(mehthod -> mehthod.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown shipping method code: " + code));
    }
}
