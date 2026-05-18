package org.solen.business.signincases;

import org.solen.business.exceptions.UserNotFoundByEmailException;
import org.solen.business.repos.IUserRepository;
import org.solen.configuration.security.JwtUtil;
import org.solen.controller.dto.auth.SignInRequest;
import org.solen.controller.dto.auth.SignInResponse;
import org.solen.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignInUseCaseImplTest {

    @Mock
    private IUserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private SignInUseCaseImpl signInUseCaseImpl;

    String email;
    String password;
    User user;
    String token;

    @BeforeEach
    void setUp() {
        email = "john@gmail.com";
        password = "password";

        user = User.builder()
                .id(1L)
                .name("john")
                .email(email)
                .password(password)
                .isAdmin(false)
                .build();
        token = UUID.randomUUID().toString();
    }

    @Test
    void signIn_success() {
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(user.getEmail(), user.getId())).thenReturn(token);

        SignInRequest request = SignInRequest.builder()
                .email(user.getEmail())
                .password(password)
                .build();

        SignInResponse response = signInUseCaseImpl.signIn(request);

        assertEquals(token, response.getToken());
    }

    @Test
    void signIn_userIsNull() {
        when(userRepository.findByEmail(email)).thenReturn(null);

        SignInRequest request = SignInRequest.builder()
                .email(user.getEmail())
                .password(password)
                .build();

        UserNotFoundByEmailException exception = assertThrows(UserNotFoundByEmailException.class, () -> signInUseCaseImpl.signIn(request));

        assertEquals("User with email john@gmail.com not found", exception.getMessage());
    }

    @Test
    void signIn_wrongPassword() {
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(false);

        SignInRequest request = SignInRequest.builder()
                .email(user.getEmail())
                .password(password)
                .build();

        UserNotFoundByEmailException exception = assertThrows(UserNotFoundByEmailException.class, () -> signInUseCaseImpl.signIn(request));

        assertEquals("User with email john@gmail.com not found", exception.getMessage());
    }
}