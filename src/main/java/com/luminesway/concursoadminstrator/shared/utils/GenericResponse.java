package com.luminesway.concursoadminstrator.shared.utils;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class GenericResponse<T> {
    private String message;
    private HttpStatus status;
    private int code;
    private T data;
}
