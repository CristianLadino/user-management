package com.cristian.user_management.infrastructure.controllers;

import com.cristian.user_management.domain.exception.UserNotFoundException;
import com.cristian.user_management.domain.gateway.UserDatabaseGateway;
import com.cristian.user_management.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserDatabaseGateway userDatabaseGateway;

    @GetMapping
    public List<User> getAllUsers(){
        return userDatabaseGateway.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userDatabaseGateway.findById(id)
                .map(ResponseEntity
                        .status(HttpStatus.OK)::body)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody  User user){
        return userDatabaseGateway.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id){
        Optional<User> userOptional = userDatabaseGateway.findById(id);
        if (userOptional.isPresent()){
            return userDatabaseGateway.save(
                    User.builder()
                            .username(user.getUsername())
                            .password(user.getPassword()).build());
        }
        return null;
    }

    @DeleteMapping
    public void deleteUser(Long id) {

    }
}
