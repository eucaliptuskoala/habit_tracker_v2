package org.example.habit_tracker.business.usercases;

import org.example.habit_tracker.business.exceptions.EmailAlreadyExistsException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.controller.dto.user.CreateUserRequest;
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
class CreateUserUseCaseImplTest {

    @Mock
    private IUserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    CreateUserRequest request;

    @BeforeEach
    void setUp() {
        request = new CreateUserRequest();
        request.setName("Test User");
        request.setPassword("password");
        request.setEmail("email@email.com");
        request.setAdmin(false);
    }

    @Test
    void createUser_successfully_created (){
        when(repository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("password");

        User savedUser = User.builder()
                .id(1L)
                .name("Test User")
                .email("email@email.com")
                .password("password")
                .isAdmin(false)
                .build();

        when(repository.save(any(User.class))).thenReturn(savedUser);

        User user = createUserUseCase.createUser(request);

        assertNotNull(user);

        assertNotNull(user.getId());
        assertEquals("Test User", user.getName());
        assertEquals("password", user.getPassword());
        assertEquals("email@email.com", user.getEmail());
        assertFalse(user.isAdmin());

        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_email_already_exists (){
        when(repository.existsByEmail(request.getEmail())).thenReturn(true);

        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> createUserUseCase.createUser(request));

        assertEquals("Email email@email.com already exists", exception.getMessage());

        verify(repository, never()).save(any(User.class));
    }

    @Test
    void createUser_request_is_null (){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> createUserUseCase.createUser(null));
        assertEquals("Request cannot be null", exception.getMessage());
        verify(repository, never()).save(any(User.class));
    }


}