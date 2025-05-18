package com.cristian.user_management.infrastructure.gateway.jpa.mapper;

import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.infrastructure.gateway.jpa.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMap {
    User toModel(UserEntity entity);
    UserEntity toEntity(User model);
}
