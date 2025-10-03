package intern.rikkei.warehousesystem.dto.response;

import java.util.List;

public record DictionaryResponseDTO(
        List<DictionaryItemDTO> content
) {
}
