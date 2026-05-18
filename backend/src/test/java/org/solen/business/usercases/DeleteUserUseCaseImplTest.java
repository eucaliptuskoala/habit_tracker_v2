package org.example.habit_tracker.business.usercases;

import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseImplTest {
    @Mock
    private IUserRepository repository;

    @InjectMocks
    private DeleteUserUseCaseImpl deleteUserUseCase;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setAdmin(false);
    }

    @Test
    void deleteUser_deleted_successfully() {
        when(repository.existsById(1L)).thenReturn(true);
        deleteUserUseCase.deleteUser(1L);

        verify(repository, times(1)).deleteById(1L);

    }

    @Test
    void deleteUser_user_does_not_exist() {
        when(repository.existsById(1L)).thenReturn(false);
        UserNotFoundByIdException exception = assertThrows(UserNotFoundByIdException.class, () -> deleteUserUseCase.deleteUser(1L));

        assertEquals("User with id 1 does not exist", exception.getMessage());
    }
}