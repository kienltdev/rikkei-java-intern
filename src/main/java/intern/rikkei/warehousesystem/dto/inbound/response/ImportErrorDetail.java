package intern.rikkei.warehousesystem.dto.inbound.response;

public record ImportErrorDetail(
        int rowNumber,
        String message
) {
}