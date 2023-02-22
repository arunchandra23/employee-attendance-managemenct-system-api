package com.arun.eamsrest.exception;

import com.arun.eamsrest.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse apiResponse = ApiResponse.builder()
                .data(new ArrayList<>())
                .errors(Arrays.asList(errors))
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(Boolean.FALSE)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {

        ApiResponse apiResponse = ApiResponse.builder()
                .data(new ArrayList<>())
                .errors(Arrays.asList(exception.getLocalizedMessage()))
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .success(Boolean.FALSE)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiResponse> badRequestExceptionHandler(BadRequestException exception) {

        ApiResponse apiResponse = ApiResponse.builder()
                .data(new ArrayList<>())
                .errors(Arrays.asList(exception.getLocalizedMessage()))
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .success(Boolean.FALSE)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}
