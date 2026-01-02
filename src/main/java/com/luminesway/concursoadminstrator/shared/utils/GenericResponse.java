package com.luminesway.concursoadminstrator.shared.utils;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Represents a generic response structure commonly used for HTTP responses.
 * This class provides a standardized way to encapsulate response data,
 * including a message, HTTP status, response code, and additional data.
 *
 * @param <T> the type of the data payload included in the response
 */
@Getter
@Builder
public class

GenericResponse<T> {
    private String message;
    private HttpStatus status;
    private int code;
    private T data;
}
