package org.example.habit_tracker.business.habitcases.creationstrategy;

import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
import org.example.habit_tracker.domain.habits.Habit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitCreationStrategyServiceTest {

    @Mock
    private IHabitCreationStrategy templateStrategy;

    @Mock
    private IHabitCreationStrategy customStrategy;

    private HabitCreationStrategyService strategyService;

    Long userId;
    CreateHabitRequest requestWithTemplate;
    CreateHabitRequest requestCustom;
    Habit habitTemplate;
    Habit habitCustom;

    @BeforeEach
    void setUp() {

        strategyService = new HabitCreationStrategyService(templateStrategy, customStrategy);

        habitTemplate = Habit.builder().name("templateHabit").build();
        habitCustom = Habit.builder().name("customHabit").build();

        userId = 1L;

        requestWithTemplate = CreateHabitRequest.builder()
                .habitTemplateId(1L)
                .description("desc")
                .build();

        requestCustom = CreateHabitRequest.builder()
                .habitTemplateId(null)
                .description("desc")
                .build();
    }

    @Test
    void getStrategy_usesTemplateStrategy() {
        when(templateStrategy.createHabit(requestWithTemplate, userId)).thenReturn(habitTemplate);

        Habit result = strategyService.getStrategy(requestWithTemplate, userId);

        verify(templateStrategy, times(1)).createHabit(requestWithTemplate, userId);
        verify(customStrategy, never()).createHabit(any(), any());
        assertEquals(habitTemplate, result);
    }

    @Test
    void getStrategy_usesCustomStrategy() {
        when(customStrategy.createHabit(requestCustom, userId)).thenReturn(habitCustom);

        Habit result = strategyService.getStrategy(requestCustom, userId);

        verify(customStrategy, times(1)).createHabit(requestCustom, userId);
        verify(templateStrategy, never()).createHabit(any(), any());
        assertEquals(habitCustom, result);
    }

    @Test
    void getStrategy_nullRequest_throws() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> strategyService.getStrategy(null, userId));
        assertEquals("Request is null", ex.getMessage());
    }
}