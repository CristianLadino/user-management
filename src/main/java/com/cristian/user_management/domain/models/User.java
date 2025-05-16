package com.cristian.user_management.domain.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {
    Long id;

    String username;

    String password;
}
