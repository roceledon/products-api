package com.falabella.api.product.common.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class NotFoundExceptionTest {
    @Test
    public void shouldGetMessageWorks() throws NotFoundException {
        NotFoundException NotFoundException = new NotFoundException("sku");
        assert NotFoundException.getMessage().equals("sku");
    }

    @Test
    public void shouldGetMessageFails() throws NotFoundException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            NotFoundException NotFoundException = new NotFoundException(null);
            NotFoundException.getMessage().equals("sku");
        });
    }

    @Test
    public void shouldGetStatusCodeWorks() {
        NotFoundException NotFoundException = new NotFoundException("test");
        assert NotFoundException.getStatusCode() == HttpStatus.NOT_FOUND.value();
    }

    @Test
    public void shouldGetStatusCodeFails() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            NotFoundException NotFoundException = null;
            assert NotFoundException.getStatusCode() == HttpStatus.NOT_FOUND.value();
        });
    }
}
