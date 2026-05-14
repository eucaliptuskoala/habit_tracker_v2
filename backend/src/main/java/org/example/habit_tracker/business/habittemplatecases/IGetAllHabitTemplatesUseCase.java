package org.example.habit_tracker.business.habittemplatecases;

import org.example.habit_tracker.domain.habits.HabitTemplate;

import java.util.List;

public interface IGetAllHabitTemplatesUseCase {
    List<HabitTemplate> getAllHabitTemplates();
}