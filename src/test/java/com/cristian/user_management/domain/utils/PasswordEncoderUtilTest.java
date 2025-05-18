package com.cristian.user_management.domain.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class PasswordEncoderUtilTest {

    @Mock
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    void encrypt_shouldReturnEncodedPassword() {
        var passwordEncoderUtil = new PasswordEncoderUtil(passwordEncoder);

        var rawPassword = "mypassword";
        var encodedPassword = "$2a$10$abc123fakeencoded";

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        var result = passwordEncoderUtil.encrypt(rawPassword);

        assertEquals(encodedPassword, result);
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }
}
