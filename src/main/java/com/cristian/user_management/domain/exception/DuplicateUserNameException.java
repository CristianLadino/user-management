package com.cristian.user_management.domain.exception;

import org.springframework.http.HttpStatus;

public class DuplicateUserNameException extends DomainException {
    public DuplicateUserNameException(String username) {
        super("The username:%s is duplicated".formatted(username), HttpStatus.NOT_FOUND);
    }
}
