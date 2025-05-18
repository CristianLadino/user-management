package com.cristian.user_management.domain.usecases.request;

import com.cristian.user_management.domain.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserRequest {

    @NotNull
    @NotBlank
    String username;

    @ValidPassword
    String password;

}
