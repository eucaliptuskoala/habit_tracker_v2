package org.example.habit_tracker.business.habitcases;

import org.example.habit_tracker.domain.habits.Habit;

public interface IUpdateStreakUseCase {
    Habit updateStreak(Long id);
}
