package org.solen.business.usercases;

import org.solen.business.exceptions.UserNotFoundByIdException;
import org.solen.business.repos.IUserRepository;
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