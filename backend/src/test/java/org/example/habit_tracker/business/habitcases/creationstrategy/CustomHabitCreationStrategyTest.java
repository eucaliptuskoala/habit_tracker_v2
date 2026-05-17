package org.example.habit_tracker.business.habitcases.creationstrategy;

import org.example.habit_tracker.business.exceptions.HabitAlreadyExistsException;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomHabitCreationStrategyTest {

    @Mock
    IHabitRepository habitRepository;

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    CustomHabitCreationStrategy customHabitCreationStrategy;

    CreateHabitRequest createHabitRequest;
    Long userId;
    User user;
    Habit habit;
    List<Habit> existingHabits;

    @BeforeEach
    void setUp() {
        userId = 1L;
        user =  User.builder()
                .id(1L)
                .name("test1")
                .email("test@mail.com")
                .password("12345")
                .isAdmin(false)
                .build();

        createHabitRequest = CreateHabitRequest.builder()
                .name("Test1")
                .description("Test1")
                .build();

        habit = Habit.builder()
                .name(createHabitRequest.getName())
                .description(createHabitRequest.getDescription())
                .streak(0)
                .lastUpdatedStreak(null)
                .thresholdDays(1)
                .creator(user)
                .build();

        existingHabits = new ArrayList<>();

        Habit habit1 = Habit.builder()
                .id(1L)
                .name("Test2")
                .description("Test2")
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(1)
                .creator(user)
                .build();
        existingHabits.add(habit1);

        Habit habit2 = Habit.builder()
                .id(2L)
                .name("Test2")
                .description("Test2")
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(1)
                .creator(user)
                .build();
        existingHabits.add(habit2);

        Habit habit3 = Habit.builder()
                .id(3L)
                .name("Test3")
                .description("Test3")
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(1)
                .creator(user)
                .build();
        existingHabits.add(habit3);

        Habit habit4 = Habit.builder()
                .id(3L)
                .name("Test1")
                .description("Test1")
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(1)
                .creator(user)
                .build();
        existingHabits.add(habit4);
    }

    @Test
    void createHabit_success(){
        when(userRepository.findById(userId)).thenReturn(user);
        when(habitRepository.findByCreatorId(user.getId())).thenReturn(existingHabits.stream().limit(3).toList());

        customHabitCreationStrategy.createHabit(createHabitRequest, userId);

        verify(userRepository, times(1)).findById(userId);
        verify(habitRepository, times(1)).save(habit);

        assertEquals("Test1", habit.getName());
    }

    @Test
    void createHabit_userNotFound()
    {
        when(userRepository.findById(userId)).thenReturn(null);

        UserNotFoundByIdException exception = assertThrows(UserNotFoundByIdException.class, ()-> customHabitCreationStrategy.createHabit(createHabitRequest, userId));

        assertEquals("User with id 1 does not exist", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verifyNoInteractions(habitRepository);
    }

    @Test
    void createHabit_nameAlreadyExists(){
        when(userRepository.findById(userId)).thenReturn(user);
        when(habitRepository.findByCreatorId(user.getId())).thenReturn(existingHabits);

        HabitAlreadyExistsException exception = assertThrows(HabitAlreadyExistsException.class, ()-> customHabitCreationStrategy.createHabit(createHabitRequest, userId));

        assertEquals("Habit Already Exists!", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(habitRepository, never()).save(habit);
    }
}