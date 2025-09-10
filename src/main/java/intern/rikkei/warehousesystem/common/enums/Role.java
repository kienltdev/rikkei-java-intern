package intern.rikkei.warehousesystem.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN(1),
    STAFF(2);

    private int value;

    public static Role fromValue(int value){
        return Stream.of(Role.values())
                .filter(role -> role.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown role value: " + value));
    }
}
