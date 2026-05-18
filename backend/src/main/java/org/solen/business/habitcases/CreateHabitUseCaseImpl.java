package org.solen.business.habitcases;

import lombok.AllArgsConstructor;
import org.solen.business.habitcases.creationstrategy.HabitCreationStrategyService;
import org.solen.controller.dto.habit.CreateHabitRequest;
import org.solen.domain.habits.Habit;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateHabitUseCaseImpl implements ICreateHabitUseCase {

    private HabitCreationStrategyService strategyService;

    @Override
    public Habit createHabit(CreateHabitRequest request, Long userId) {
        return strategyService.getStrategy(request, userId);
    }
}