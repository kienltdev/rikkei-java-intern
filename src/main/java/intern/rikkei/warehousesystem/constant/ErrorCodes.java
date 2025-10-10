package intern.rikkei.warehousesystem.constant;

public final class ErrorCodes {

    private ErrorCodes() {}
    // Root Codes
    public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
    public static final String BUSINESS_ERROR = "BUSINESS_ERROR";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String ACCESS_DENIED = "ACCESS_DENIED";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String METHOD_NOT_ALLOWED = "METHOD_NOT_ALLOWED";
    public static final String INVALID_PARAMETER_FORMAT = "INVALID_PARAMETER_FORMAT";
    public static final String MALFORMED_REQUEST_BODY = "MALFORMED_REQUEST_BODY";
    public static final String MISSING_REQUIRED_PARAMETER = "MISSING_REQUIRED_PARAMETER";

    // Field Codes - Business
    public static final String USERNAME_ALREADY_EXISTS = "USERNAME_ALREADY_EXISTS";
    public static final String EMAIL_ALREADY_EXISTS = "EMAIL_ALREADY_EXISTS";
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
}