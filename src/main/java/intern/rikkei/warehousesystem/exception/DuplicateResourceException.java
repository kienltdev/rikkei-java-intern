package intern.rikkei.warehousesystem.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends BusinessException {
    public DuplicateResourceException(String code, String message) {
        super(HttpStatus.CONFLICT, code, message);
    }
}
