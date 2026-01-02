package com.luminesway.concursoadminstrator.shared.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterDTO {
    private String field;       // name of the field to filter
    private String operator;    // eq, ne, like, gt, lt, in, etc.
    private Object value;       // value to compare
    private String logical;     // AND, OR (for multiple filters)

    public FilterDTO() {}

    public FilterDTO(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        this.logical = "AND"; // default
    }
}
