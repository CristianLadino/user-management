package com.cristian.user_management.domain.usecases.user;

import com.cristian.user_management.domain.exception.DuplicateUserNameException;
import com.cristian.user_management.domain.gateway.UserDatabaseGateway;
import com.cristian.user_management.domain.mapper.UserResponseMap;
import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.domain.usecases.request.CreateUserRequest;
import com.cristian.user_management.domain.usecases.response.UserResponse;
import com.cristian.user_management.domain.utils.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUser {

    private final UserDatabaseGateway userDatabaseGateway;
    private final PasswordEncoderUtil passwordEncoderUtil;
    private final UserResponseMap userResponseMap;

    public UserResponse execute(CreateUserRequest createUserRequest){
        if (userDatabaseGateway.existByUsernameAndIdNot(createUserRequest.getUsername(), 0L)){
            throw new DuplicateUserNameException(createUserRequest.getUsername());
        }


        return userResponseMap.toUserResponse(userDatabaseGateway.save(mapToModel(createUserRequest)));
    }

    private User mapToModel(CreateUserRequest createUserRequest){
        return User.builder()
                .username(createUserRequest.getUsername())
                .password(passwordEncoderUtil.encrypt(createUserRequest.getPassword()))
                .build();
    }
}
