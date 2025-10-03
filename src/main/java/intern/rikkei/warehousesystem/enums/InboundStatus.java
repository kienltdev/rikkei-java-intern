package intern.rikkei.warehousesystem.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum InboundStatus {
    NOT_OUTBOUND(0),
    PARTIALLY_OUTBOUND(1),
    FULLY_OUTBOUND(2);


    private final int code;

    @JsonValue
    public int getCode() {
        return code;
    }

    public static InboundStatus fromCode(int code) {
        return Stream.of(InboundStatus.values())
                .filter(status -> status.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown inbound status code : " + code));
    }

}
