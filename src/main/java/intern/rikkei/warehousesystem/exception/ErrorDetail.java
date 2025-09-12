package intern.rikkei.warehousesystem.exception;

public record ErrorDetail(
        String fieldName,
        String message
) {
}
