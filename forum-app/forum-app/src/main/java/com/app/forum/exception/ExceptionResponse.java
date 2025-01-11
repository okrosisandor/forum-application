package com.app.forum.exception;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExceptionResponse {

	private String message;
    private String details;
    private LocalDateTime timestamp;
    private Set<String> validationErrors;
}
