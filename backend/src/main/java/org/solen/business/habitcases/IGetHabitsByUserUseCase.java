package org.solen.business.habitcases;

import org.solen.domain.habits.Habit;

import java.util.List;

public interface IGetHabitsByUserUseCase {
    List<Habit> getHabitsByUser(Long userId);
}
