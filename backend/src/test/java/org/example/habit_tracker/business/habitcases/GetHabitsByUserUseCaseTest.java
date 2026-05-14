package org.example.habit_tracker.business.habitcases;

import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetHabitsByUserUseCaseTest {

    @Mock
    private IHabitRepository repository;

    @Mock
    private StreakValidator streakValidator;

    @InjectMocks
    private GetHabitsByUserUseCaseImpl getHabitsByUserUseCase;

    List<Habit> habits;

    @BeforeEach
    void setUp() {

        habits = new ArrayList<>();

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
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(1)
                .creator(user)
                .build();
        habits.add(habit);

        Habit habit2 = Habit.builder()
                .id(1L)
                .name("Test1")
                .description("Test1")
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(1)
                .creator(user)
                .build();
        habits.add(habit2);

        Habit habit3 = Habit.builder()
                .id(1L)
                .name("Test1")
                .description("Test1")
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(1)
                .creator(user)
                .build();
        habits.add(habit3);
    }

    @Test
    void getHabitsByUser(){

        when(repository.findByCreatorId(1L)).thenReturn(habits);

        getHabitsByUserUseCase.getHabitsByUser(1L);

        verify(repository, times(1)).findByCreatorId(1L);
        verify(streakValidator, times(3)).validateStreak(any(Habit.class));

        assertEquals(3,  habits.size());
    }
}