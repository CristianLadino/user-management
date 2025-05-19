package com.cristian.user_management.domain.mapper;

import com.cristian.user_management.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
class UserResponseMapTest {
    UserResponseMap userResponseMap;

    @BeforeEach
    void setUp() {
        userResponseMap = Mappers.getMapper(UserResponseMap.class);
    }

    @Test
    void toUserResponse_shouldMapFieldsCorrectly() {
        var user = User.builder()
                .id(1L)
                .username("cristian")
                .password("secret")
                .build();

        var response = userResponseMap.toUserResponse(user);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("cristian", response.getUsername());
    }

    @Test
    void toUserResponseList_shouldMapListCorrectly() {
        var user1 = User.builder().id(1L).username("cristian").password("123").build();
        var user2 = User.builder().id(2L).username("julio").password("456").build();
        var userList = List.of(user1, user2);

        var responseList = userResponseMap.toUserResponseList(userList);

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals("cristian", responseList.get(0).getUsername());
        assertEquals("julio", responseList.get(1).getUsername());
    }

    @Test
    void toUserResponse_whenInputIsNull_shouldReturnNull() {
        var response = userResponseMap.toUserResponse(null);
        assertNull(response);
    }

    @Test
    void toUserResponseList_whenInputIsNull_shouldReturnNull() {
        var responseList = userResponseMap.toUserResponseList(null);
        assertNull(responseList);
    }
}