package com.cristian.user_management.domain.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class PasswordConstraintValidatorTest {

    private PasswordConstraintValidator validator;

    @Test
    void validPassword_shouldReturnTrue() {
        validator = new PasswordConstraintValidator();
        assertTrue(validator.isValid("Abcd1234@", null));
    }

    @Test
    void invalidPassword_shouldReturnFalse() {
        validator = new PasswordConstraintValidator();
        assertFalse(validator.isValid("abc", null));
    }

    @Test
    void nullPassword_shouldReturnFalse() {
        validator = new PasswordConstraintValidator();
        assertFalse(validator.isValid(null, null));
    }
}
