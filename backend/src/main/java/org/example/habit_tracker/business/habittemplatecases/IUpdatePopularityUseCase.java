package org.example.habit_tracker.business.habittemplatecases;

import org.example.habit_tracker.domain.habits.HabitTemplate;

public interface IUpdatePopularityUseCase {
    HabitTemplate updatePopularity(Long id);
}