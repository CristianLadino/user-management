package com.cristian.user_management.infrastructure.gateway.jpa;

import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.infrastructure.gateway.jpa.entities.UserEntity;
import com.cristian.user_management.infrastructure.gateway.jpa.mapper.UserMap;
import com.cristian.user_management.infrastructure.gateway.jpa.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class UserDatabaseGatewayImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMap userMap;

    @InjectMocks
    private UserDatabaseGatewayImpl userDatabaseGateway;


    @Test
    void findAll_shouldReturnMappedUsers() {
        var entity = UserEntity
                .builder()
                .id(1L)
                .username("Carlos")
                .password("34215")
                .build();

        var user = User
                .builder()
                .id(1L)
                .username("cristian")
                .password("pass")
                .build();

        when(userRepository.findAll()).thenReturn(List.of(entity));
        when(userMap.toModel(entity)).thenReturn(user);

        var result = userDatabaseGateway.findAll();

        assertEquals(1, result.size());
        assertEquals(user, result.getFirst());
        verify(userRepository, times(1)).findAll();
        verify(userMap, times(1)).toModel(entity);
    }

    @Test
    void findById_whenUserExists_shouldReturnMappedUser() {
        var id = 1L;
        var entity = UserEntity
                .builder()
                .id(1L)
                .username("cristian")
                .password("34215")
                .build();
        User user = User.builder().id(id).username("cristian").password("34215").build();

        when(userRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userMap.toModel(entity)).thenReturn(user);

        var result = userDatabaseGateway.findById(id);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(id);
        verify(userMap, times(1)).toModel(entity);
    }

    @Test
    void findById_whenUserDoesNotExist_shouldReturnEmptyOptional() {
        var userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        var result = userDatabaseGateway.findById(userId);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findById(userId);
        verifyNoInteractions(userMap);
    }

    @Test
    void existByUsernameAndIdNot_shouldReturnTrueIfExists() {
        when(userRepository.existsByUsernameAndIdNot("cristian", 1L)).thenReturn(true);

        boolean result = userDatabaseGateway.existByUsernameAndIdNot("cristian", 1L);

        assertTrue(result);
        verify(userRepository, times(1)).existsByUsernameAndIdNot("cristian", 1L);
    }

    @Test
    void save_shouldMapAndSaveAndReturnModel() {
        User user = User.builder().id(1L).username("cristian").password("1234").build();
        var entity = UserEntity
                .builder()
                .id(1L)
                .username("cristian")
                .password("34215")
                .build();
        var savedEntity = UserEntity
                .builder()
                .id(1L)
                .username("cristian")
                .password("34215")
                .build();

        when(userMap.toEntity(user)).thenReturn(entity);
        when(userRepository.save(entity)).thenReturn(savedEntity);
        when(userMap.toModel(savedEntity)).thenReturn(user);

        User result = userDatabaseGateway.save(user);

        assertEquals(user, result);
        verify(userMap).toEntity(user);
        verify(userRepository).save(entity);
        verify(userMap).toModel(savedEntity);
    }

    @Test
    void deleteById_shouldCallRepository() {
        var id = 1L;

        userDatabaseGateway.deleteById(id);

        verify(userRepository).deleteById(id);
    }
}
