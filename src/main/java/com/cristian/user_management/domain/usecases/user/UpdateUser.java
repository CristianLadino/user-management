package com.cristian.user_management.domain.usecases.user;

import com.cristian.user_management.domain.exception.UserNotFoundException;
import com.cristian.user_management.domain.gateway.UserDatabaseGateway;
import com.cristian.user_management.domain.mapper.UserResponseMap;
import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.domain.usecases.request.UpdateUserRequest;
import com.cristian.user_management.domain.usecases.response.UserResponse;
import com.cristian.user_management.domain.utils.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UpdateUser {

    private final UserDatabaseGateway userDatabaseGateway;
    private final PasswordEncoderUtil passwordEncoderUtil;
    private final UserResponseMap userResponseMap;

    public UserResponse execute(UpdateUserRequest updateUserRequest, Long id){
        var currentUser = userDatabaseGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        var password = passwordEncoderUtil.encrypt(updateUserRequest.getPassword());
        return userResponseMap.toUserResponse(userDatabaseGateway.save(mapToModel(currentUser.getUsername(), password, id)));
    }

    private User mapToModel(String username, String password, Long id){

        return User.builder().
                id(id)
                .username(username)
                .password(password)
                .build();
    }

}
