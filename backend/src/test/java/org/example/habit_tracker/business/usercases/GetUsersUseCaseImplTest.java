package org.example.habit_tracker.business.usercases;

import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUsersUseCaseImplTest {

    @Mock
    private IUserRepository repository;

    @InjectMocks
    private GetUsersUseCaseImpl getUsersUseCase;

    List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();

        User user1 = User.builder()
                .id(1L)
                .name("Test1")
                .email("Test1@example.com")
                .password("Test1")
                .isAdmin(false)
                .build();
        users.add(user1);

        User user2 = User.builder()
                .id(2L)
                .name("Test2")
                .email("Test2@example.com")
                .password("Test2")
                .isAdmin(false)
                .build();
        users.add(user2);
    }

    @Test
    void getUsersUseCase_successful() {
        when(repository.findAll()).thenReturn(users);

        List<User> retrievedUsers = getUsersUseCase.getUsers();

        assertEquals(retrievedUsers.size(), users.size());
        assertEquals(retrievedUsers.get(0), users.get(0));
        assertEquals(retrievedUsers.get(1), users.get(1));

        verify(repository, times(1)).findAll();
    }
}