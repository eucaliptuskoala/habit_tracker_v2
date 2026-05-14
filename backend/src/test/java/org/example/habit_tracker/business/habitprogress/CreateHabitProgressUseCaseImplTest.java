package org.example.habit_tracker.business.habitprogress;

import org.example.habit_tracker.business.exceptions.HabitNotFoundByIdException;
import org.example.habit_tracker.business.repos.IHabitProgressRepository;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateHabitProgressUseCaseImplTest {
    @Mock
    private IHabitProgressRepository progressRepository;

    @Mock
    private IHabitRepository habitRepository;

    @InjectMocks
    private CreateHabitProgressUseCaseImpl createUseCase;

    private Habit habit;

    @BeforeEach
    void setUp() {
        habit = Habit.builder()
                .id(1L)
                .name("Test Habit")
                .thresholdDays(3)
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.now())
                .build();
    }

    @Test
    void createProgressSuccess() {
        HabitProgress savedProgress = HabitProgress.builder()
                .habit(habit)
                .date(habit.getLastUpdatedStreak().toLocalDate())
                .streakValue(habit.getStreak())
                .createdAt(LocalDateTime.now())
                .build();

        when(habitRepository.findById(habit.getId())).thenReturn(habit);
        when(progressRepository.save(any())).thenReturn(savedProgress);

        HabitProgress created = createUseCase.create(habit.getId());

        assertNotNull(created);
        assertEquals(habit.getId(), created.getHabit().getId());
        assertEquals(habit.getStreak(), created.getStreakValue());
        verify(habitRepository, times(1)).findById(habit.getId());
        verify(progressRepository, times(1)).save(any());
    }

    @Test
    void createProgressHabitNotFound() {
        when(habitRepository.findById(habit.getId())).thenReturn(null);

        assertThrows(HabitNotFoundByIdException.class, () -> createUseCase.create(habit.getId()));
        verify(progressRepository, never()).save(any());
    }
}