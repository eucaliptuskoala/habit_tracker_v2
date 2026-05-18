package org.example.habit_tracker.business.usercases;

import org.example.habit_tracker.business.exceptions.UserNotFoundByEmailException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private IUserRepository repository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    User user;
    User admin;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("User")
                .email("user@gmail.com")
                .password("password")
                .isAdmin(false)
                .build();

        admin = User.builder()
                .id(2L)
                .name("Admin")
                .email("admin@gmail.com")
                .password("password")
                .isAdmin(true)
                .build();
    }

    @Test
    void loadByEmail_user_success(){
        when(repository.findByEmail(user.getEmail())).thenReturn(user);

        UserDetails userDetails = userDetailsServiceImpl.loadByEmail(user.getEmail());

        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        verify(repository).findByEmail(user.getEmail());
    }

    @Test
    void loadByEmail_admin_success(){
        when(repository.findByEmail(admin.getEmail())).thenReturn(admin);

        UserDetails userDetails = userDetailsServiceImpl.loadByEmail(admin.getEmail());

        assertNotNull(userDetails);
        assertEquals(admin.getEmail(), userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
        verify(repository).findByEmail(admin.getEmail());
    }

    @Test
    void loadByEmail_userNotFoundByEmail(){
        when(repository.findByEmail(null)).thenReturn(null);

        Exception exception = assertThrows(UserNotFoundByEmailException.class, () -> userDetailsServiceImpl.loadByEmail(null));

        assertEquals("User with email null not found", exception.getMessage());
        verify(repository).findByEmail(null);
    }
}