package com.falabella.api.product.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends Throwable {
    private static final long serialVersionUID = 1L;

    public BadRequestException (String message) {
        super(message);
    }

    public int getStatusCode() {
        return HttpStatus.BAD_REQUEST.value();
    }
}
