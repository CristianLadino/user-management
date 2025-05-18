package com.cristian.user_management.domain.usecases.request;

import com.cristian.user_management.domain.validation.ValidPassword;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateUserRequest {
    @ValidPassword
    String password;
}
