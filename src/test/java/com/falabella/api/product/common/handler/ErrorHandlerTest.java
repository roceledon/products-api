package com.falabella.api.product.common.handler;

import com.falabella.api.product.common.exception.BadRequestException;
import com.falabella.api.product.common.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ErrorHandlerTest {
    private ErrorHandler errorHandler = Mockito.mock(ErrorHandler.class);

    @Test
    public void testBadRequestExceptionWorks() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Mockito.when(errorHandler.handleBadRequestException(new BadRequestException("test"))).thenReturn(null);
            ErrorHandler errorHandler = new ErrorHandler();
            errorHandler.handleBadRequestException(new BadRequestException("test"));
            Mockito.verify(errorHandler, Mockito.times(1)).handleBadRequestException(new BadRequestException("test"));
        });
    }

    @Test
    public void testhandleNotFoundExceptionWorks() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Mockito.when(errorHandler.handleNotFoundException(new NotFoundException("test"))).thenReturn(null);
            ErrorHandler errorHandler = new ErrorHandler();
            errorHandler.handleNotFoundException(new NotFoundException("test"));
            Mockito.verify(errorHandler, Mockito.times(1)).handleNotFoundException(new NotFoundException("test"));
        });
    }

    @Test
    public void testExceptionWorks() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            Mockito.when(errorHandler.handleException(new Exception())).thenReturn(null);
            ErrorHandler errorHandler = new ErrorHandler();
            errorHandler.handleException(new Exception("test"));
            Mockito.verify(errorHandler, Mockito.times(1)).handleException(new Exception("test"));
        });
    }

}
