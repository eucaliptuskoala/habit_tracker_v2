package org.solen.business.habitcases.creationstrategy;

import org.solen.controller.dto.habit.CreateHabitRequest;
import org.solen.domain.habits.Habit;

public interface IHabitCreationStrategy {
    Habit createHabit(CreateHabitRequest request, Long userId);
}