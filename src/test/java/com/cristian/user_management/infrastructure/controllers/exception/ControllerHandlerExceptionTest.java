package com.cristian.user_management.infrastructure.controllers.exception;

import com.cristian.user_management.domain.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class ControllerHandlerExceptionTest {

    private ControllerHandlerException exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new ControllerHandlerException();
    }

    @Test
    void handleDomainException_shouldReturnApiErrorResponse() {
        var errorMessage = "Username already exists";
        var status = HttpStatus.BAD_REQUEST;

        var exception = new DomainException(errorMessage, status);

        var response = exceptionHandler.handleDomainException(exception);

        assertNotNull(response);
        assertEquals(status.value(), response.getStatusCode().value());
        assertNotNull(response.getBody());

        ApiError apiError = response.getBody();
        assertEquals(errorMessage, apiError.getError());
        assertEquals("Business error", apiError.getMessage());
        assertEquals(status.value(), apiError.getStatus());
    }
}
