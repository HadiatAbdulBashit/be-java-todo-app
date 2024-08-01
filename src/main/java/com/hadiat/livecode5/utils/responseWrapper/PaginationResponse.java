package com.hadiat.livecode5.utils.responseWrapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PaginationResponse<T> {
    private List<T> items;
    private Long size;
    private Integer currentPage;
    private Integer totalPages;

    public PaginationResponse(Page<T> page) {
        this.items = page.getContent();
        this.size = page.getTotalElements();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
    }
}
