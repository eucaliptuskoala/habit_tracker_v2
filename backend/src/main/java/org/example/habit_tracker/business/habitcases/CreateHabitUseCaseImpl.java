package org.example.habit_tracker.business.habitcases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.habitcases.creationstrategy.HabitCreationStrategyService;
import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
import org.example.habit_tracker.domain.habits.Habit;
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