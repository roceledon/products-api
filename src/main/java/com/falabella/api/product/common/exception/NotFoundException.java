package com.falabella.api.product.common.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends Throwable {
    private static final long serialVersionUID = 1L;

    public NotFoundException (String message) {
        super(message);
    }

    public int getStatusCode() {
        return HttpStatus.NOT_FOUND.value();
    }
}
