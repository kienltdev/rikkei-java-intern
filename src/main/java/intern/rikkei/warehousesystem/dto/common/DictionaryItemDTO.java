package intern.rikkei.warehousesystem.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a single item in a dictionary, typically for a dropdown list.")
public record DictionaryItemDTO(
        @Schema(description = "The actual value or code of the item to be sent to the server.", example = "VN")
        Object code,
        @Schema(description = "The human-readable display name for the item.", example = "Vietnam")
        String name
) {
}
