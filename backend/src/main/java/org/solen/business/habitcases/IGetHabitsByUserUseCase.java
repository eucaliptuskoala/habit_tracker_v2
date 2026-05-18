package org.example.habit_tracker.business.habitcases;

import org.example.habit_tracker.domain.habits.Habit;

import java.util.List;

public interface IGetHabitsByUserUseCase {
    List<Habit> getHabitsByUser(Long userId);
}
