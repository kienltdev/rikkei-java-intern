package intern.rikkei.warehousesystem.exception;

import org.springframework.http.HttpStatus;

public class InvalidOperationException extends BusinessException {
    public InvalidOperationException(String code, String message) {
        super(HttpStatus.BAD_REQUEST, code, message);
    }
}
