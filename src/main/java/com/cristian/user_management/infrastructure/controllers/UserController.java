package com.cristian.user_management.infrastructure.controllers;

import com.cristian.user_management.domain.gateway.UserDatabaseGateway;
import com.cristian.user_management.domain.mapper.UserResponseMap;
import com.cristian.user_management.domain.usecases.request.CreateUserRequest;
import com.cristian.user_management.domain.usecases.request.UpdateUserRequest;
import com.cristian.user_management.domain.usecases.response.UserResponse;
import com.cristian.user_management.domain.usecases.user.CreateUser;
import com.cristian.user_management.domain.usecases.user.DeleteUser;
import com.cristian.user_management.domain.usecases.user.UpdateUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Operations related to user management, such as creating, retrieving, updating and deleting users.")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserDatabaseGateway userDatabaseGateway;
    private final CreateUser createUser;
    private final UpdateUser updateUser;
    private final DeleteUser deleteUser;
    private final UserResponseMap userResponseMap;

    @Operation(
            summary = "Get all users",
            description = "Returns a list of all registered users in the system."
    )
    @ApiResponse(responseCode = "200",
            content = {
                @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(
                                schema = @Schema(implementation = UserResponse.class)
                        )
                )
            }
    )
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userResponseMap.toUserResponseList(userDatabaseGateway.findAll()));
    }

    @Operation(
            summary = "Get user by ID",
            description = "Returns a user based on the provided ID."
    )

    @ApiResponse(responseCode = "200",
            content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponse.class)
                )
            }
    )

    @ApiResponse(responseCode = "404",description = "User not found", content = @Content)

    @Parameter(
            name = "id",
            description = "ID of the user to retrieve",
            required = true,
            example = "1"
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return userDatabaseGateway.findById(id)
                .map(user -> ResponseEntity.ok(userResponseMap.toUserResponse(user)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Create a new user",
            description = "Registers a new user in the system. Only accessible to users with the ADMIN role."
    )


    @ApiResponse(
            responseCode = "201",
            description = "User successfully created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResponse.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    @ApiResponse(responseCode = "409", description = "Username already exists", content = @Content)
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User data to create",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = CreateUserRequest.class)
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest createUserRequest){
        return new ResponseEntity<>(createUser.execute(createUserRequest), HttpStatus.CREATED);
    }


    @Operation(
            summary = "Update user password",
            description = "Updates only the password of the user identified by the given ID. Username cannot be changed."
    )

    @ApiResponse(
                    responseCode = "202",
                    description = "User successfully update",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class)
                    )
    )
    @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)

    @Parameter(
            name = "id",
            description = "ID of the user to update",
            required = true,
            example = "1"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "New password for the user",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = UpdateUserRequest.class)
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest updateUserRequest, @PathVariable Long id){
        return new ResponseEntity<>(updateUser.execute(updateUserRequest, id), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete user by ID",
            description = "Deletes the user identified by the given ID. Returns the ID of the deleted user upon success."
    )

    @ApiResponse(
            responseCode = "200",
            description = "User successfully delete",
            content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "{\"deletedUserId\": 1}")
            )
    )
    @ApiResponse(responseCode = "404", description = "User not found", content = @Content)

    @Parameter(
            name = "id",
            description = "ID of the user to delete",
            required = true,
            example = "1"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(deleteUser.execute(id), HttpStatus.OK);
    }
}
