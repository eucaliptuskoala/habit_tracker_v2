package org.solen.business.habitcases;

import org.solen.controller.dto.habit.CreateHabitRequest;
import org.solen.domain.habits.Habit;

public interface ICreateHabitUseCase {
    Habit createHabit(CreateHabitRequest request, Long userId);
}