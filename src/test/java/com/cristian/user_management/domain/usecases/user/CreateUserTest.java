package com.cristian.user_management.domain.usecases.user;

import com.cristian.user_management.domain.exception.DuplicateUserNameException;
import com.cristian.user_management.domain.mapper.UserResponseMap;
import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.domain.usecases.request.CreateUserRequest;
import com.cristian.user_management.domain.usecases.response.UserResponse;
import com.cristian.user_management.domain.utils.PasswordEncoderUtil;
import com.cristian.user_management.infrastructure.gateway.jpa.UserDatabaseGatewayImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class CreateUserTest {

    @Mock
    private UserDatabaseGatewayImpl userDatabaseGatewayImpl;

    @Mock
    private PasswordEncoderUtil passwordEncoderUtil;

    @Mock
    private UserResponseMap userResponseMap;

    @InjectMocks
    private CreateUser createUser;

    @Test
    void execute_shouldCreateUserSuccessfully() {
        var request = CreateUserRequest.builder()
                .username("cristian")
                .password("32145")
                .build();

        var savedUser = User.builder()
                .id(0L)
                .username("cristian")
                .password("encodedPass")
                .build();

        var expectedResponse = UserResponse.builder()
                .id(0L)
                .username("cristian")
                .build();

        when(userDatabaseGatewayImpl.existByUsernameAndIdNot("cristian", 0L))
                .thenReturn(false);
        when(passwordEncoderUtil.encrypt("32145"))
                .thenReturn("encodedPass");
        when(userDatabaseGatewayImpl.save(any()))
                .thenReturn(savedUser);
        when(userResponseMap.toUserResponse(savedUser))
                .thenReturn(expectedResponse);

        var result = createUser.execute(request);

        assertEquals(expectedResponse, result);
        verify(userDatabaseGatewayImpl, times(1)).existByUsernameAndIdNot("cristian",0L);
        verify(passwordEncoderUtil, times(1)).encrypt("32145");
        verify(userDatabaseGatewayImpl, times(1)).save(any(User.class));
        verify(userResponseMap, times(1)).toUserResponse(savedUser);
    }

    @Test
    void execute_whenUsernameExists_shouldThrowException() {
        var request = CreateUserRequest.builder()
                .username("cristian")
                .password("32145")
                .build();

        when(userDatabaseGatewayImpl.existByUsernameAndIdNot("cristian", 0L))
                .thenReturn(true);

        var exist = assertThrows(DuplicateUserNameException.class, () ->
                createUser.execute(request)
        );

        verify(userDatabaseGatewayImpl, times(1)).existByUsernameAndIdNot("cristian",0L);
        verifyNoMoreInteractions(userDatabaseGatewayImpl, passwordEncoderUtil, userResponseMap);
        assertEquals("The username:cristian is duplicated", exist.getMessage());
    }
}
