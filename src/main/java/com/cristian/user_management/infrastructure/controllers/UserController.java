package com.cristian.user_management.infrastructure.controllers;

import com.cristian.user_management.domain.exception.UserNotFoundException;
import com.cristian.user_management.domain.gateway.UserDatabaseGateway;
import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.domain.usecases.request.UserRequest;
import com.cristian.user_management.domain.usecases.response.UserResponse;
import com.cristian.user_management.domain.usecases.user.CreateUser;
import com.cristian.user_management.domain.usecases.user.DeleteUser;
import com.cristian.user_management.domain.usecases.user.UpdateUser;
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
    private final CreateUser createUser;
    private final UpdateUser updateUser;
    private final DeleteUser deleteUser;

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userDatabaseGateway.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return userDatabaseGateway.findById(id)
                .map(ResponseEntity
                        .status(HttpStatus.OK)::body)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(createUser.execute(userRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id){
        return new ResponseEntity<>(updateUser.execute(userRequest, id), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteUser(Long id) {
        return new ResponseEntity<>(deleteUser.execute(id), HttpStatus.OK);
    }
}
