package com.luminesway.concursoadminstrator.shared.dtos.response;

import lombok.Builder;
import lombok.Getter;

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
    private int status;
    private int code;
    private T data;
}
