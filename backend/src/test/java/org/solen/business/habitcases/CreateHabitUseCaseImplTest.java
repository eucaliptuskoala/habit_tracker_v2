package org.solen.business.habitcases;

import org.solen.business.habitcases.creationstrategy.HabitCreationStrategyService;
import org.solen.controller.dto.habit.CreateHabitRequest;
import org.solen.domain.habits.Habit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateHabitUseCaseImplTest {

    @Mock
    private HabitCreationStrategyService strategyService;

    private CreateHabitUseCaseImpl useCase;

    CreateHabitRequest request;
    Long userId;
    Habit habit;

    @BeforeEach
    void setUp() {
        useCase = new CreateHabitUseCaseImpl(strategyService);
        userId = 1L;
        request = CreateHabitRequest.builder().description("desc").build();
        habit = Habit.builder().name("habit").build();
    }

    @Test
    void createHabit_delegatesToStrategyService() {
        when(strategyService.getStrategy(request, userId)).thenReturn(habit);

        Habit result = useCase.createHabit(request, userId);

        verify(strategyService, times(1)).getStrategy(request, userId);
        assertEquals(habit, result);
    }
}