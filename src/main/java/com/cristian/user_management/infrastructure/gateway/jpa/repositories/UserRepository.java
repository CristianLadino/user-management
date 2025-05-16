package com.cristian.user_management.infrastructure.gateway.jpa.repositories;

import com.cristian.user_management.infrastructure.gateway.jpa.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
