package intern.rikkei.warehousesystem.dto.common;

import java.util.List;

public record DictionaryResponseDTO(
        List<DictionaryItemDTO> content
) {
}
