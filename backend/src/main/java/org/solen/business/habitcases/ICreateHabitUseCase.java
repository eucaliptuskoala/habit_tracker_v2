package org.example.habit_tracker.business.habitcases;

import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
import org.example.habit_tracker.domain.habits.Habit;

public interface ICreateHabitUseCase {
    Habit createHabit(CreateHabitRequest request, Long userId);
}