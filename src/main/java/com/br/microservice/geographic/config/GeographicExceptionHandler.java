package com.br.microservice.geographic.config;

import com.br.microservice.geographic.data.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.TimeoutException;


@RestControllerAdvice
public class GeographicExceptionHandler {

    @ExceptionHandler(value = {TimeoutException.class})
    public ResponseEntity<ResponseError> handleTimeoutException(TimeoutException ex) {
        return new ResponseEntity<>(ResponseError.builder().code(404).message("Error").build(), HttpStatus.NOT_FOUND);
    }
}