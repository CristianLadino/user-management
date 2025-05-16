package com.cristian.user_management.domain.gateway;

import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.infrastructure.gateway.jpa.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserDatabaseGateway {
    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById (Long Id);
}
