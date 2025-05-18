package com.cristian.user_management.domain.usecases.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserRequest {

    String username;

    String password;

}
