package com.cristian.user_management.domain.usecases.user;

import com.cristian.user_management.domain.exception.UserNotFoundException;
import com.cristian.user_management.domain.gateway.UserDatabaseGateway;
import com.cristian.user_management.domain.mapper.UserResponseMap;
import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.domain.usecases.request.UpdateUserRequest;
import com.cristian.user_management.domain.usecases.response.UserResponse;
import com.cristian.user_management.domain.utils.PasswordEncoderUtil;
import com.cristian.user_management.infrastructure.gateway.jpa.UserDatabaseGatewayImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UpdateUserTest {

    @Mock
    private UserDatabaseGatewayImpl userDatabaseGatewayImpl;

    @Mock
    private PasswordEncoderUtil passwordEncoderUtil;

    @Mock
    private UserResponseMap userResponseMap;

    @InjectMocks
    private UpdateUser updateUser;


    @Test
    void execute_whenUserExists_shouldUpdatePassword() {
        var id = 1L;
        var oldUsername = "cristian";
        var newPassword = "newPass123";
        var encryptedPassword = "enc_newPass123";

        var request = UpdateUserRequest.builder()
                .password(newPassword)
                .build();

        var currentUser = User.builder()
                .id(id)
                .username(oldUsername)
                .password("old")
                .build();

        var updatedUser = User.builder()
                .id(id)
                .username(oldUsername)
                .password(encryptedPassword)
                .build();

        var expectedResponse = UserResponse.builder()
                .id(id)
                .username(oldUsername)
                .build();

        when(userDatabaseGatewayImpl.findById(id)).thenReturn(Optional.of(currentUser));
        when(passwordEncoderUtil.encrypt(newPassword)).thenReturn(encryptedPassword);
        when(userDatabaseGatewayImpl.save(updatedUser)).thenReturn(updatedUser);
        when(userResponseMap.toUserResponse(updatedUser)).thenReturn(expectedResponse);

        UserResponse actualResponse = updateUser.execute(request, id);

        assertEquals(expectedResponse, actualResponse);
        verify(userDatabaseGatewayImpl, times(1)).findById(id);
        verify(passwordEncoderUtil, times(1)).encrypt(newPassword);
        verify(userDatabaseGatewayImpl, times(1)).save(updatedUser);
        verify(userResponseMap, times(1)).toUserResponse(updatedUser);
        verifyNoMoreInteractions(userDatabaseGatewayImpl, passwordEncoderUtil, userResponseMap);
    }

    @Test
    void execute_whenUserDoesNotExist_shouldThrowException() {
        var userId = 4L;
        var request = UpdateUserRequest.builder()
                .password("irrelevant")
                .build();

        when(userDatabaseGatewayImpl.findById(userId)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            updateUser.execute(request, userId);
        });

        assertEquals("User not found with id: 4", exception.getMessage());
        verify(userDatabaseGatewayImpl).findById(userId);
        verifyNoMoreInteractions(userDatabaseGatewayImpl, passwordEncoderUtil, userResponseMap);
    }
}
