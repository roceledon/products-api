package com.falabella.api.product.common.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class BadRequestExceptionTest {
    @Test
    public void shouldGetMessageWorks() {
        BadRequestException badRequestException = new BadRequestException("test");
        assert badRequestException.getMessage().equals("test");
    }

    @Test
    public void shouldGetMessageFails() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            BadRequestException badRequestException = new BadRequestException(null);
            badRequestException.getMessage().equals("test");
        });
    }

    @Test
    public void shouldGetStatusCodeWorks() {
        BadRequestException badRequestException = new BadRequestException("test");
        assert badRequestException.getStatusCode() == HttpStatus.BAD_REQUEST.value();
    }

    @Test
    public void shouldGetStatusCodeFails() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            BadRequestException badRequestException = null;
            assert badRequestException.getStatusCode() == HttpStatus.BAD_REQUEST.value();
        });
    }
}
