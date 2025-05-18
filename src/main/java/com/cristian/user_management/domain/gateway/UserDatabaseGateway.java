package com.cristian.user_management.domain.gateway;

import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.domain.usecases.response.UserResponse;
import com.cristian.user_management.infrastructure.gateway.jpa.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDatabaseGateway {
    List<UserResponse> findAll();

    Optional<UserResponse> findById(Long id);

    boolean existByUsernameAndIdNot(String username, Long id);

    UserResponse save(User user);

    void deleteById (Long Id);
}
