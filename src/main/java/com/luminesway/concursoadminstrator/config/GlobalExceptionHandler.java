package com.luminesway.concursoadminstrator.config;


import com.luminesway.concursoadminstrator.shared.dtos.response.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ErrorResponse errorResponse = new ErrorResponse(
                "Error de validación",
                "Los datos enviados no son válidos",
                errors
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

//    @ExceptionHandler(NotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<ErrorResponse> handleClienteNotFound(NotFoundException ex) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                "Registro no encontrado",
//                ex.getMessage(),
//                null
//        );
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
//    }




    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleDatabaseException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Ya existe un registro con ese dato",
                extraerDetailCompleto(ex.getMessage()),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Error interno del servidor",
                "Ha ocurrido un error inesperado. Por favor contacte al administrador",
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private String extraerDetailCompleto(String errorMessage) {
        Pattern detailPattern = Pattern.compile("Detail: (.+?)(?=\\]|$)");
        Matcher matcher = detailPattern.matcher(errorMessage);

        if (matcher.find()) {
            return matcher.group(1).trim();
        }

        return null;
    }
}