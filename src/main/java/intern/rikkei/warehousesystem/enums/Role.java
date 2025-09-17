package intern.rikkei.warehousesystem.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum Role {
    ADMIN(1),
    STAFF(2);

    private final int value;

    @JsonValue
    public int getValue() {
        return value;
    }

    public static Role fromValue(int value){
        return Stream.of(Role.values())
                .filter(role -> role.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown role value: " + value));
    }
}
