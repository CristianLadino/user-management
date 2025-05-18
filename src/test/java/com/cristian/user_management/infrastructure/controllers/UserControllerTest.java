package com.cristian.user_management.infrastructure.controllers;


import com.cristian.user_management.domain.gateway.UserDatabaseGateway;
import com.cristian.user_management.domain.mapper.UserResponseMap;
import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.domain.usecases.request.CreateUserRequest;
import com.cristian.user_management.domain.usecases.request.UpdateUserRequest;
import com.cristian.user_management.domain.usecases.response.UserResponse;
import com.cristian.user_management.domain.usecases.user.CreateUser;
import com.cristian.user_management.domain.usecases.user.DeleteUser;
import com.cristian.user_management.domain.usecases.user.UpdateUser;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UserControllerTest {

    @Mock
    private UserDatabaseGateway userDatabaseGateway;

    @Mock
    private CreateUser createUser;

    @Mock
    private UpdateUser updateUser;

    @Mock
    private DeleteUser deleteUser;

    @Mock
    private UserResponseMap userResponseMap;

    @InjectMocks
    private UserController userController;

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void getAllUsers_shouldReturnUserResponseList() {
        var mockUser = User
                .builder()
                .id(1L)
                .username("cristian")
                .password("123456")
                .build();

        var mockUserResponse = UserResponse
                .builder()
                .id(1L)
                .username("cristian")
                .build();

        var users = List.of(mockUser);
        var responses = List.of(mockUserResponse);

        when(userDatabaseGateway.findAll()).thenReturn(users);
        when(userResponseMap.toUserResponseList(users))
                .thenReturn(responses);

        var response = userController.getAllUsers();

        verify(userDatabaseGateway, times(1)).findAll();
        verify(userResponseMap, times(1)).toUserResponseList(users);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(responses, response.getBody());
    }

    @Test
    void getUserById_whenFound_shouldReturnUserResponse() {
        var id = 3L;
        var mockUser = User
                .builder()
                .id(3L)
                .username("Pedro")
                .password("123456")
                .build();

        var mockUserResponse = UserResponse
                .builder()
                .id(3L)
                .username("Pedro")
                .build();

        when(userDatabaseGateway.findById(id)).thenReturn(Optional.of(mockUser));
        when(userResponseMap.toUserResponse(mockUser)).thenReturn(mockUserResponse);

        var response = userController.getUserById(id);

        verify(userDatabaseGateway, times(1)).findById(id);
        verify(userResponseMap, times(1)).toUserResponse(mockUser);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockUserResponse, response.getBody());
    }

    @Test
    void getUserById_whenNotFound_shouldReturn404() {
        var id = 1L;

        when(userDatabaseGateway.findById(id)).thenReturn(Optional.empty());

        var response = userController.getUserById(id);

        verify(userDatabaseGateway, times(1)).findById(id);
        verifyNoInteractions(userResponseMap);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    void createUser_shouldReturnCreatedUserResponse() {
        var mockCreateUserRequest = CreateUserRequest
                .builder()
                .username("Julio")
                .password("32145")
                .build();

        var mockUserResponse = UserResponse
                .builder()
                .id(1L)
                .username("Julio")
                .build();

        when(createUser.execute(mockCreateUserRequest)).thenReturn(mockUserResponse);

        var result = userController.createUser(mockCreateUserRequest);

        verify(createUser, times(1)).execute(mockCreateUserRequest);
        assertEquals(201, result.getStatusCode().value());
        assertEquals(mockUserResponse, result.getBody());
    }

    @Test
    void validateCreateUserRequest_whenPasswordIsValid_shouldPassValidation() {
        var request = CreateUserRequest.builder()
                .username("cristian")
                .password("Valid123!")
                .build();

        var violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "Should not have constraint violations");
    }

    @Test
    void validateCreateUserRequest_whenPasswordIsInvalid_shouldFailValidation() {
        var request = CreateUserRequest.builder()
                .username("cristian")
                .password("abc")
                .build();

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Should have constraint violations");
        assertTrue(violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("password")
        ));
    }

    @Test
    void validateCreateUserRequest_whenPasswordIsNull_shouldFailValidation() {
        var request = CreateUserRequest.builder()
                .username("cristian")
                .password(null)
                .build();

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "Should have constraint violations for null password");
    }

    @Test
    void updateUser_shouldReturnUpdatedUserResponse() {
        var id = 1L;
        var mockUpdateUserRequest = UpdateUserRequest
                .builder()
                .password("321654")
                .build();
        var mockUserResponse = UserResponse
                .builder()
                .id(1L)
                .username("Julio")
                .build();

        when(updateUser.execute(mockUpdateUserRequest, id)).thenReturn(mockUserResponse);

        var result = userController.updateUser(mockUpdateUserRequest, id);

        verify(updateUser, times(1)).execute(mockUpdateUserRequest, id);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(mockUserResponse, result.getBody());
    }

    @Test
    void validateUpdateUserRequest_whenPasswordIsValid_shouldPassValidation() {
        var request = UpdateUserRequest.builder()
                .password("NewPass123!")
                .build();

        var violations = validator.validate(request);

        assertTrue(violations.isEmpty());
    }

    @Test
    void validateUpdateUserRequest_whenPasswordIsTooShort_shouldFailValidation() {
        var request = UpdateUserRequest.builder()
                .password("123")
                .build();

        var violations = validator.validate(request);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deleteUser_shouldReturnDeletedUserId() {
        var id = 1L;

        when(deleteUser.execute(id)).thenReturn(id);

        var response = userController.deleteUser(id);

        verify(deleteUser).execute(id);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(id, response.getBody());
    }
}
