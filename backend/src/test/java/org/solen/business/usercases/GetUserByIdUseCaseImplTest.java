package org.example.habit_tracker.business.usercases;

import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserByIdUseCaseImplTest {

    @Mock
    private IUserRepository repository;

    @InjectMocks
    private GetUserByIdUseCaseImpl getUserByIdUseCase;

    @Test
    void getUserById_successfull(){

        User mockUser = User.builder()
                .id(1L)
                .name("test")
                .email("test@example.com")
                .password("test")
                .build();

        when(repository.findById(1L)).thenReturn(mockUser);

        User user = getUserByIdUseCase.getUserById(1L);

        verify(repository, times(1)).findById(1L);

        assertNotNull(user);
        assertEquals(1L, user.getId());
    }

    @Test
    void getUserById_failed(){
        UserNotFoundByIdException exception = assertThrows(UserNotFoundByIdException.class, () -> getUserByIdUseCase.getUserById(2L));
        assertEquals("User with id 2 does not exist", exception.getMessage());

    }
}