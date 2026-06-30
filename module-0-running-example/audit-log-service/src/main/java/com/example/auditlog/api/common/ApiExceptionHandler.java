package com.example.auditlog.api.common;

import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
class ApiExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ApiError> validationError(MethodArgumentNotValidException exception) {
    var message =
        exception.getBindingResult().getFieldErrors().stream()
            .map(this::fieldErrorMessage)
            .collect(Collectors.joining("; "));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(message));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  ResponseEntity<ApiError> typeMismatch(MethodArgumentTypeMismatchException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            new ApiError(
                "Invalid value '"
                    + exception.getValue()
                    + "' for parameter '"
                    + exception.getName()
                    + "'"));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  ResponseEntity<ApiError> illegalArgument(IllegalArgumentException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(exception.getMessage()));
  }

  private String fieldErrorMessage(FieldError error) {
    return error.getField() + " " + error.getDefaultMessage();
  }
}

