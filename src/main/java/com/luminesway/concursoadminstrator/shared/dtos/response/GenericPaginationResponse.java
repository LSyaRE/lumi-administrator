package com.luminesway.concursoadminstrator.shared.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GenericPaginationResponse<T> {
    private String message;
    private int status;
    private List<T> data;
    private int totalPages;
    private int currentPage;
    private long totalElements;
    private int pageSize;
}
