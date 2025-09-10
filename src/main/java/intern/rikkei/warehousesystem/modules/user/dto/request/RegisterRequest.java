package intern.rikkei.warehousesystem.modules.user.dto.request;

public record RegisterRequest(String username, String password, String fullName, String email) {
}
