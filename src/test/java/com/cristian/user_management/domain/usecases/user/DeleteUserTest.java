package com.cristian.user_management.domain.usecases.user;

import com.cristian.user_management.domain.exception.UserNotFoundException;
import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.infrastructure.gateway.jpa.UserDatabaseGatewayImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith({MockitoExtension.class})
class DeleteUserTest {

    @Mock
    private UserDatabaseGatewayImpl userDatabaseGatewayImpl;

    @InjectMocks
    private DeleteUser deleteUser;

    @Test
    void execute_whenUserExists_shouldDeleteAndReturnId(){
        var id = 1L;

        var mockUser = User
                .builder()
                .id(3L)
                .username("Pedro")
                .password("123456")
                .build();

        var expectedResponse = 1L;

        when(userDatabaseGatewayImpl.findById(id))
                .thenReturn(Optional.of(mockUser));
        doNothing().when(userDatabaseGatewayImpl).deleteById(id);

        var result = deleteUser.execute(id);

        assertEquals(expectedResponse, result);
        verify(userDatabaseGatewayImpl, times(1)).findById(id);
        verify(userDatabaseGatewayImpl, times(1)).deleteById(id);
    }

    @Test
    void execute_whenUserDoesNotExist_shouldThrowException() {
        var id = 1L;

        when(userDatabaseGatewayImpl.findById(id))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            deleteUser.execute(id);
        });

        assertEquals("User not found with id: 1", exception.getMessage());
        verify(userDatabaseGatewayImpl, times(1)).findById(id);
        verifyNoMoreInteractions(userDatabaseGatewayImpl);
    }
}