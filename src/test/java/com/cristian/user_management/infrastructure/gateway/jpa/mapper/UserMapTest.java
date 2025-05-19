package com.cristian.user_management.infrastructure.gateway.jpa.mapper;

import com.cristian.user_management.domain.models.User;
import com.cristian.user_management.infrastructure.gateway.jpa.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class UserMapTest {
    UserMap userMap;

    @BeforeEach
    void setUp() {
        userMap = Mappers.getMapper(UserMap.class);
    }

    @Test
    void toModel_shouldMapFieldsCorrectly() {
        var entity = UserEntity.builder()
                .id(1L)
                .username("cristian")
                .password("123456")
                .build();

        var model = userMap.toModel(entity);

        assertNotNull(model);
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getUsername(), model.getUsername());
        assertEquals(entity.getPassword(), model.getPassword());
    }

    @Test
    void toEntity_shouldMapFieldsCorrectly() {
        var user = User.builder()
                .id(2L)
                .username("julio")
                .password("abcdef")
                .build();

        var entity = userMap.toEntity(user);

        assertNotNull(entity);
        assertEquals(user.getId(), entity.getId());
        assertEquals(user.getUsername(), entity.getUsername());
        assertEquals(user.getPassword(), entity.getPassword());
    }

    @Test
    void toModel_whenInputIsNull_shouldReturnNull() {
        var model = userMap.toModel(null);
        assertNull(model);
    }

    @Test
    void toEntity_whenInputIsNull_shouldReturnNull() {
        var entity = userMap.toEntity(null);
        assertNull(entity);
    }

}