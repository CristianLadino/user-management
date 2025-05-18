package com.cristian.user_management.domain.usecases.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponse {
    Long id;
    
    String username;
}
