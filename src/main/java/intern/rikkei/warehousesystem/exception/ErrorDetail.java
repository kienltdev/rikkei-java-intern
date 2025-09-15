package intern.rikkei.warehousesystem.exception;

public record ErrorDetail(
        String field,
        String code,
        String message
) {
}
