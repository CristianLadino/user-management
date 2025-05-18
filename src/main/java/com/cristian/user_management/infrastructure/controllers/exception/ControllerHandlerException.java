package com.cristian.user_management.infrastructure.controllers.exception;


import com.cristian.user_management.domain.exception.DomainException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHandlerException {
  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerHandlerException.class);

  @ExceptionHandler(DomainException.class)
  public ResponseEntity<ApiError> handleDomainException(DomainException e){
      var apiError = ApiError.builder()
              .error(e.getMessage())
              .message("Business error")
              .status(e.getStatus().value())
              .build();

      LOGGER.error("[method: handleDomainException] [error: {}] [status: {}]", e.getMessage(), e.getStatus().value());

      return ResponseEntity.status(apiError.getStatus()).body(apiError);
  }
}
