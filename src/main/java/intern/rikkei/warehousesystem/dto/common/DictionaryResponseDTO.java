package intern.rikkei.warehousesystem.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Response containing a list of dictionary items.")
public record DictionaryResponseDTO(
        @Schema(description = "A list of dictionary items.")
        List<DictionaryItemDTO> content
) {
}
