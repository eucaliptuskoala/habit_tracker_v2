package org.example.habit_tracker.business.habitcases.creationstrategy;

import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
import org.example.habit_tracker.domain.habits.Habit;

public interface IHabitCreationStrategy {
    Habit createHabit(CreateHabitRequest request, Long userId);
}