package org.example.habit_tracker.business.habittemplatecases;

import org.example.habit_tracker.domain.habits.Habit;

public interface ICreateHabitTemplateUseCase {
    void createHabitTemplate(Habit habit);
}