package org.example.habit_tracker.business.habitcases;

import org.example.habit_tracker.business.exceptions.StreakAlreadyUpdatedException;
import org.example.habit_tracker.business.habitprogress.ICreateHabitProgressUseCase;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.domain.habits.Habit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateStreakUseCaseImplTest {

    @Mock
    private IHabitRepository repository;

    @Mock
    private ICreateHabitProgressUseCase createProgress;

    @Mock
    private StreakValidator streakValidator;

    @InjectMocks
    private UpdateStreakUseCaseImpl updateStreakUseCaseImpl;

    @Test
    void updateStreakUseCase_success() {
        Habit habit = Habit.builder()
                .id(1L)
                .name("Test1")
                .description("Test1")
                .streak(1)
                .lastUpdatedStreak(null)
                .thresholdDays(1)
                .creator(null)
                .build();

        when(repository.findById(1L)).thenReturn(habit);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Habit updated = updateStreakUseCaseImpl.updateStreak(1L);

        assertEquals(2, updated.getStreak());
        assertNotNull(updated.getLastUpdatedStreak());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(habit);
        verify(createProgress, times(1)).create(habit.getId());
    }

    @Test
    void updateStreakUseCase_streakAlreadyUpdated() {
        Habit habit = Habit.builder()
                .id(1L)
                .name("Test1")
                .description("Test1")
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(1)
                .creator(null)
                .build();

        when(repository.findById(1L)).thenReturn(habit);

        StreakAlreadyUpdatedException exception =
                assertThrows(StreakAlreadyUpdatedException.class, () -> updateStreakUseCaseImpl.updateStreak(1L));

        assertEquals("Streak already updated!", exception.getMessage());
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any());
        verify(createProgress, never()).create(any());
    }
}
