package org.example.habit_tracker.business.habittemplatecases;

import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.business.repos.IHabitTemplateRepository;
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
class CreateHabitTemplateUseCaseImplTest {

    @Mock
    private IHabitTemplateRepository habitTemplateRepository;

    @Mock
    private IHabitRepository habitRepository;

    @InjectMocks
    private CreateHabitTemplateUseCaseImpl  createHabitTemplateUseCaseImpl;

    User user;
    Habit habit;
    HabitTemplate habitTemplate;
    List<Habit> habits =  new ArrayList<>();

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(1L)
                .name("Test1")
                .email("test@mail.com")
                .password("12345")
                .isAdmin(false)
                .build();

        habitTemplate = HabitTemplate.builder()
                .id(1L)
                .name("Test1")
                .popularity(1)
                .build();

        habit = Habit.builder()
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

        Habit habit4 = Habit.builder()
                .id(1L)
                .name("Test1")
                .description("Test1")
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(1)
                .creator(user)
                .build();
        habits.add(habit4);
    }

    @Test
    void createHabitTemplate_success() {
        when(habitRepository.findByName(habit.getName())).thenReturn(habits);
        when(habitTemplateRepository.existsByName(habit.getName())).thenReturn(false);

        createHabitTemplateUseCaseImpl.createHabitTemplate(habit);

        verify(habitRepository, times(1)).findByName(habit.getName());
        verify(habitTemplateRepository, times(1)).existsByName(habit.getName());
        verify(habitTemplateRepository)
                .save(argThat(ht ->
                        ht.getName().equals("Test1") &&
                                ht.getPopularity() == 4
                ));

        assertEquals(habit.getName(), habitTemplate.getName());
    }

    @Test
    void createHabitTemplate_template_already_exists() {
        when(habitRepository.findByName(habit.getName())).thenReturn(habits);
        when(habitTemplateRepository.existsByName(habit.getName())).thenReturn(true);

        createHabitTemplateUseCaseImpl.createHabitTemplate(habit);

        verify(habitRepository, times(1)).findByName(habit.getName());
        verify(habitTemplateRepository, times(1)).existsByName(habit.getName());
        verify(habitTemplateRepository, never()).save(habitTemplate);
    }

    @Test
    void createHabitTemplate_not_enough_templates() {
        List<Habit> limited = habits.stream().limit(2).toList();

        when(habitRepository.findByName(habit.getName())).thenReturn(limited);
        when(habitTemplateRepository.existsByName(habit.getName())).thenReturn(false);

        createHabitTemplateUseCaseImpl.createHabitTemplate(habit);

        verify(habitRepository, times(1)).findByName(habit.getName());
        verify(habitTemplateRepository, times(1)).existsByName(habit.getName());
        verify(habitTemplateRepository, never()).save(habitTemplate);
    }
}