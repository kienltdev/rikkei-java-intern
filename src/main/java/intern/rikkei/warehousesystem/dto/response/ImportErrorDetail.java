package intern.rikkei.warehousesystem.dto.response;

public record ImportErrorDetail(
        int rowNumber,
        String message
) {
}