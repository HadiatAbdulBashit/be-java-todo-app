package com.hadiat.livecode5.utils.responseWrapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PaginationUserResponse<T> {
    private List<T> users;
    private Long totalItems;
    private Integer currentPage;
    private Integer totalPages;

    public PaginationUserResponse(Page<T> page) {
        this.users = page.getContent();
        this.totalItems = page.getTotalElements();
        this.currentPage = page.getNumber();
        this.totalPages = page.getTotalPages();
    }
}
