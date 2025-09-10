package intern.rikkei.warehousesystem.common.constant;

public class ApiConstants {
    private ApiConstants() {}
    // API Versions
//    public static final String API_VERSION_1 = "/api/v1";
//    public static final String API_VERSION_2 = "/api/v2";

    // API Paths
//    public static final String AUTH_BASE = API_VERSION_1 + "/auth";
//    public static final String PRODUCT_BASE = API_VERSION_1 + "/products";
//    public static final String ORDER_BASE = API_VERSION_1 + "/orders";
//    public static final String USER_BASE = API_VERSION_1 + "/users";

    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;
    public static final String DEFAULT_SORT_BY = "createdAt";
    public static final String DEFAULT_SORT_DIRECTION = "DESC";

    // Headers
    public static final String HEADER_API_KEY = "X-API-Key";
    public static final String HEADER_REQUEST_ID = "X-Request-Id";
    public static final String HEADER_CLIENT_VERSION = "X-Client-Version";

    // Rate Limiting
    public static final int RATE_LIMIT_PER_MINUTE = 60;
    public static final int RATE_LIMIT_PER_HOUR = 1000;
}
