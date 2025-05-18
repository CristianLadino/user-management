package com.cristian.user_management.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class DomainException extends RuntimeException {
    @Getter
    private HttpStatus status;
    public DomainException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
