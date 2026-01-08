package com.luminesway.concursoadminstrator.shared.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String title;
    private String message;
    private Object details;
    private LocalDateTime timestamp;

    public ErrorResponse(String title, String message, Object details) {
        this.title = title;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
    public ErrorResponse(String title, String message) {
        this.title = title;
        this.message = message;
        this.details = null;
        this.timestamp = LocalDateTime.now();
    }
}