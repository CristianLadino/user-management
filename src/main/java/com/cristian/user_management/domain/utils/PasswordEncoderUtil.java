package com.cristian.user_management.domain.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncoderUtil {
    private final BCryptPasswordEncoder passwordEncoder;

    public String encrypt(String password){
        return passwordEncoder.encode(password);
    }

}
