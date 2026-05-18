package org.solen.business.habitcases;

import org.solen.domain.habits.Habit;

public interface IUpdateStreakUseCase {
    Habit updateStreak(Long id);
}
