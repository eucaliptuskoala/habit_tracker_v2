package org.example.habit_tracker.business.habitcases.creationstrategy;

import org.example.habit_tracker.business.exceptions.HabitAlreadyExistsException;
import org.example.habit_tracker.business.exceptions.TemplateNotFoundException;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.habittemplatecases.ICreateHabitTemplateUseCase;
import org.example.habit_tracker.business.habittemplatecases.IUpdatePopularityUseCase;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.business.repos.IHabitTemplateRepository;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.habits.HabitTemplate;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TemplateHabitCreationStrategyTest {

    @Mock
    private IHabitTemplateRepository templateRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IHabitRepository habitRepository;

    @Mock
    private IUpdatePopularityUseCase  updatePopularityUseCase;

    @Mock
    ICreateHabitTemplateUseCase createHabitTemplateUseCase;

    @InjectMocks
    TemplateHabitCreationStrategy templateHabitCreationStrategy;

    CreateHabitRequest createHabitRequest;
    HabitTemplate habitTemplate;
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

        habitTemplate = HabitTemplate.builder()
                .id(1L)
                .name("Test1")
                .popularity(1)
                .build();

        createHabitRequest = CreateHabitRequest.builder()
                .habitTemplateId(habitTemplate.getId())
                .description("Test1")
                .build();

        habit = Habit.builder()
                .name(createHabitRequest.getName())
                .description(createHabitRequest.getDescription())
                .template(habitTemplate)
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
        when(templateRepository.findById(1L)).thenReturn(habitTemplate);
        when(userRepository.findById(userId)).thenReturn(user);

        templateHabitCreationStrategy.createHabit(createHabitRequest, userId);

        verify(templateRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(userId);

        assertEquals(habit.getTemplate(), habitTemplate);
        assertEquals(habit.getCreator(), user);
    }

    @Test
    void createHabit_templateNotFound(){
        when(templateRepository.findById(1L)).thenReturn(null);

        TemplateNotFoundException exception = assertThrows(TemplateNotFoundException.class, () -> templateHabitCreationStrategy.createHabit(createHabitRequest, userId));

        assertEquals("Template with id 1 does not exist",exception.getMessage());
    }

    @Test
    void createHabit_userNotFound(){
        when(templateRepository.findById(habitTemplate.getId())).thenReturn(habitTemplate);
        when(userRepository.findById(userId)).thenReturn(null);

        UserNotFoundByIdException exception = assertThrows(UserNotFoundByIdException.class, () -> templateHabitCreationStrategy.createHabit(createHabitRequest, userId));

        assertEquals("User with id 1 does not exist",exception.getMessage());
    }

    @Test
    void createHabit_HabitAlreadyExists(){
        when(templateRepository.findById(habitTemplate.getId())).thenReturn(habitTemplate);
        when(userRepository.findById(userId)).thenReturn(user);
        when(habitRepository.findByCreatorId(user.getId())).thenReturn(existingHabits);

        HabitAlreadyExistsException exception = assertThrows(HabitAlreadyExistsException.class, ()-> templateHabitCreationStrategy.createHabit(createHabitRequest, userId));

        assertEquals("Habit Already Exists!", exception.getMessage());

        verify(userRepository, times(1)).findById(userId);
        verify(habitRepository, never()).save(habit);
    }
}