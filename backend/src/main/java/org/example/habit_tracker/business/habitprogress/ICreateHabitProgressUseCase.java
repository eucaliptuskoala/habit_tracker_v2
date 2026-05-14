package org.example.habit_tracker.business.habitprogress;

import org.example.habit_tracker.domain.progress.HabitProgress;

public interface ICreateHabitProgressUseCase {
    HabitProgress create(Long habitId);
}
