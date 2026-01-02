package com.luminesway.concursoadminstrator.shared.utils;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class SpringResult<T> {
    @Getter
    private final T result;
    @Getter
    private final int code;
    private final String message;

    private final List<String> errors;

    /**
     * Constructs a new {@code Result} instance by initializing its fields using the provided
     * {@code ResultParameters} and response code.
     *
     * @param resultParameters an instance of {@code ResultParameters} containing the result,
     *                         message, and errors to initialize the {@code Result} object.
     * @param code the response code associated with the result.
     */
    public SpringResult(ResultParameters<T> resultParameters, int code) {
        this.result = resultParameters.result();
        this.code = code;
        this.message = resultParameters.message();
        this.errors = resultParameters.errors() != null ? resultParameters.errors() : new ArrayList<>();
    }

    /**
     * Creates a success {@link SpringResult} object instance by combining the given {@link ResultParameters}
     * and a status code.
     *
     * @param resultParameters the parameters containing the result, message, and potential errors
     * @param code the HTTP status code representing the outcome of the operation
     * @return a {@link SpringResult} instance encapsulating the provided parameters and status code
     */
    public  static <F> SpringResult<F> success(ResultParameters<F> resultParameters, int code) {
        return new SpringResult<F>(resultParameters, code);
    }

    /**
     * Creates an error result object using the given result parameters and HTTP status code.
     *
     * @param resultParameters the parameters for configuring the error result, including the result object, message, and list of errors
     * @param code the HTTP status code to associate with the error result
     * @return a {@code Result<F>} object representing an error scenario configured with the provided parameters
     */
    public  static <F> SpringResult<F> error(ResultParameters<F> resultParameters, int code) {
        return new SpringResult<F>(resultParameters, code);
    }

    /**
     * Converts the current instance of Result into a GenericResponse object.
     * The conversion incorporates attributes such as status code, message,
     * HTTP status, and appropriate data (either the result or a list of errors).
     *
     * @return a GenericResponse object containing the transformed result, its
     *         corresponding HTTP status, code, and message.
     */
    public GenericResponse<?> toJson() {
        HttpStatus status = HttpStatus.valueOf(code);

        return GenericResponse.builder()
                .code(code)
                .message(message != null ? message : status.getReasonPhrase())
                .status(status)
                .data(errors.isEmpty() ? result : errors)
                .build();
    }
}
