package org.solen.business.habitcases;

import org.solen.business.repos.IHabitRepository;
import org.solen.domain.habits.Habit;
import org.solen.domain.users.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StreakValidatorTest {

    @Mock
    private IHabitRepository repository;

    @InjectMocks
    private StreakValidator streakValidator;

    @Test
    void validateStreak_dropStreak() {
        User user =  User.builder()
                .id(1L)
                .name("Test1")
                .email("test@mail.com")
                .password("12345")
                .isAdmin(false)
                .build();

        Habit habit = Habit.builder()
                .id(1L)
                .name("Test1")
                .description("Test1")
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.of(2025, 11, 2, 0, 0))
                .thresholdDays(1)
                .creator(user)
                .build();

        streakValidator.validateStreak(habit);

        assertEquals(0, habit.getStreak());
        verify(repository, times(1)).save(habit);
    }

    @Test
    void validateStreak_doesNotDropStreak() {
        Habit habit = Habit.builder()
                .streak(5)
                .lastUpdatedStreak(LocalDateTime.now().minusDays(1))
                .thresholdDays(2)
                .build();

        streakValidator.validateStreak(habit);

        assertEquals(5, habit.getStreak());
        verify(repository, never()).save(any(Habit.class));
    }

    @Test
    void validateStreak_noLastUpdate_doesNothing() {
        Habit habit = Habit.builder()
                .streak(5)
                .lastUpdatedStreak(null)
                .thresholdDays(1)
                .build();

        streakValidator.validateStreak(habit);

        assertEquals(5, habit.getStreak());
        verify(repository, never()).save(any(Habit.class));
    }
}