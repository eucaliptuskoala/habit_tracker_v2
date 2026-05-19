package org.solen.business.habitcases;

import org.solen.business.repos.IHabitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteHabitUseCaseImplTest {

    @Mock
    IHabitRepository repository;

    @InjectMocks
    DeleteHabitUseCaseImpl deleteHabitUseCaseImpl;

    @Test
    void deleteHabit() {
        deleteHabitUseCaseImpl.deleteHabit(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}