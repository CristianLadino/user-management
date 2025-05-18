package com.cristian.user_management.infrastructure.controllers.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ApiError {
    String error;

    String message;

    Integer status;
}
