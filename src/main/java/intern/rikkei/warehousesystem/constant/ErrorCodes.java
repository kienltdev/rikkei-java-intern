package intern.rikkei.warehousesystem.constant;

public final class ErrorCodes {

    private ErrorCodes() {}

    // Authentication & Authorization
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String ACCESS_DENIED = "ACCESS_DENIED";

    // Validation
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String USERNAME_ALREADY_EXISTS = "USERNAME_ALREADY_EXISTS";
    public static final String EMAIL_ALREADY_EXISTS = "EMAIL_ALREADY_EXISTS";

    // System
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

}