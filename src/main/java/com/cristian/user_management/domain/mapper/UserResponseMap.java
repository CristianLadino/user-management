package com.cristian.user_management.domain.mapper;

import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.domain.usecases.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserResponseMap {
    UserResponse toUserResponse(User model);

    List<UserResponse> toUserResponseList(List<User> modelList);
}
