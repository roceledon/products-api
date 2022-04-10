package com.falabella.api.product.common.handler;

import com.falabella.api.product.common.domain.ResponseError;
import com.falabella.api.product.common.exception.BadRequestException;
import com.falabella.api.product.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(BadRequestException.class)
    public Mono<ResponseEntity<ResponseError>> handleBadRequestException(BadRequestException e) {
        ResponseError error = new ResponseError();
        error.setTimestamp(new Date());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage(e.getMessage());
        return Mono.just(new ResponseEntity<>(error, HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(NotFoundException.class)
    public Mono<ResponseEntity<ResponseError>> handleNotFoundException(NotFoundException e) {
        ResponseError error = new ResponseError();
        error.setTimestamp(new Date());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        error.setMessage(e.getMessage());
        return Mono.just(new ResponseEntity<>(error, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ResponseError>> handleNotFoundException(Exception e) {
        ResponseError error = new ResponseError();
        error.setTimestamp(new Date());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        error.setMessage(e.getMessage());
        return Mono.just(new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
