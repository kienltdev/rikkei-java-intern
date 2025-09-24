package intern.rikkei.warehousesystem.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ShippingMethod {
    AIR("A", "Air"),
    SEA("S", "Sea"),
    TRAIL("T", "Trail"),
    ROAD("R", "Road");

    private final String code;
    private final String name;

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
