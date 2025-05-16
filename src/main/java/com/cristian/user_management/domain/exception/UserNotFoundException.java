package com.cristian.user_management.domain.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(Long id) {
        super("Use not found with id: %s".formatted(id), HttpStatus.NOT_FOUND);
    }
}
