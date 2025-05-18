package com.cristian.user_management.domain.usecases.user;

import com.cristian.user_management.domain.exception.UserNotFoundException;
import com.cristian.user_management.domain.gateway.UserDatabaseGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUser {
    private final UserDatabaseGateway userDatabaseGateway;

    public Long execute(Long id){
        userDatabaseGateway.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        userDatabaseGateway.deleteById(id);
        return id;
    }
}
