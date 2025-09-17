package intern.rikkei.warehousesystem.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class PaginatedResponse<T> {
    private final List<T> content;
    private final int currentPage;
    private final int pageSize;
    private final int totalPages;
    private final long totalItems;

    public PaginatedResponse(List<T> content, int currentPage, int pageSize, int totalPages, long totalItems) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }
}
