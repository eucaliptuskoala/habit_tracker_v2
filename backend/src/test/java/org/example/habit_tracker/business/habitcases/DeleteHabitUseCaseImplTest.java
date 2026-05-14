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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteHabitUseCaseImplTest {

    @Mock
    IHabitRepository repository;

    @InjectMocks
    DeleteHabitUseCaseImpl deleteHabitUseCaseImpl;

    Habit habit;

    @BeforeEach
    void setUp() {
        User user =  User.builder()
                .id(1L)
                .name("Test1")
                .email("test@mail.com")
                .password("12345")
                .isAdmin(false)
                .build();

        habit = Habit.builder()
                .id(1L)
                .name("Test1")
                .description("Test1")
                .streak(1)
                .lastUpdatedStreak(null)
                .thresholdDays(1)
                .creator(user)
                .build();
    }

    @Test
    void deleteHabit() {
        deleteHabitUseCaseImpl.deleteHabit(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}