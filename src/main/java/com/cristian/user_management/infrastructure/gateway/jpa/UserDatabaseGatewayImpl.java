package com.cristian.user_management.infrastructure.gateway.jpa;

import com.cristian.user_management.domain.gateway.UserDatabaseGateway;
import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.infrastructure.gateway.jpa.mapper.UserMap;
import com.cristian.user_management.infrastructure.gateway.jpa.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDatabaseGatewayImpl implements UserDatabaseGateway {

    private final UserRepository userRepository;

    private final UserMap userMap;


    @Override
    public List<User> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMap::toModel)
                .toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id)
                .map(userMap::toModel);
    }

    @Override
    public User save(User user) {
        var userSave =  userRepository
                .save(userMap.toEntity(user));
        return userMap.toModel(userSave);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
