package intern.rikkei.warehousesystem.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum InboundStatus {
    NOT_OUTBOUND(0, "Not Outbound"),
    PARTIALLY_OUTBOUND(1,  "Partially Outbound"),
    FULLY_OUTBOUND(2,   "Fully Outbound");


    private final int code;
    private final String name;

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
