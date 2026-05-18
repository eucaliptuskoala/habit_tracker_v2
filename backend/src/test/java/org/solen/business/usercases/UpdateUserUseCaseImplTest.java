package org.example.habit_tracker.business.usercases;

import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.controller.dto.user.UpdateUserRequest;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseImplTest {

    @Mock
    private IUserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UpdateUserUseCaseImpl updateUserUseCase;

    UpdateUserRequest request;

    @BeforeEach
    void setUp() {
        request = new UpdateUserRequest();
        request.setName("Test User");
        request.setPassword("password123");
        request.setEmail("email@email.com");
    }

    @Test
    void updateUser_success() {
        User user = User.builder()
                .id(1L)
                .name("Test User")
                .password("password")
                .email("email@email.com")
                .isAdmin(false)
                .build();


        when(repository.findById(user.getId())).thenReturn(user);

        when(repository.save(user)).thenReturn(user);

        when(passwordEncoder.encode(request.getPassword())).thenReturn("password123");

        updateUserUseCase.updateUser(request, 1L);

        assertNotNull(user.getId());
        assertEquals("Test User", user.getName());
        assertEquals("email@email.com", user.getEmail());
        assertEquals("password123", user.getPassword());

        verify(repository, times(1)).findById(user.getId());
        verify(repository, times(1)).save(user);
    }

    @Test
    void updateUser_no_user_found() {
        when(repository.findById(1L)).thenReturn(null);
        UserNotFoundByIdException exception = assertThrows(UserNotFoundByIdException.class, () -> updateUserUseCase.updateUser(request, 1L));
        assertEquals("User with id 1 does not exist", exception.getMessage());
    }
}