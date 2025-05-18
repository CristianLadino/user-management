package com.cristian.user_management.domain.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConstraintValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid password. It must be at least 8 characters, contain upper and lower case letters, digits, and special characters.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
