package com.cristian.user_management.domain.usecases.user;

import com.cristian.user_management.domain.exception.DuplicateUserNameException;
import com.cristian.user_management.domain.exception.UserNotFoundException;
import com.cristian.user_management.domain.gateway.UserDatabaseGateway;
import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.domain.usecases.request.UserRequest;
import com.cristian.user_management.domain.usecases.response.UserResponse;
import com.cristian.user_management.domain.utils.PasswordEncoderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUser {

    private final UserDatabaseGateway userDatabaseGateway;
    private final PasswordEncoderUtil passwordEncoderUtil;

    public UserResponse execute(UserRequest userRequest, Long id){
        userDatabaseGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (userDatabaseGateway.existByUsernameAndIdNot(userRequest.getUsername(), id)){
            throw new DuplicateUserNameException(userRequest.getUsername());
        }

        return userDatabaseGateway.save(mapToModel(userRequest));
    }

    private User mapToModel(UserRequest userRequest){
        return User.builder().
                username(userRequest.getUsername()).
                password(passwordEncoderUtil.encrypt(userRequest.getPassword()))
                .build();
    }

}
