package org.example.habit_tracker.business.habittemplatecases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.business.repos.IHabitTemplateRepository;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreateHabitTemplateUseCaseImpl implements ICreateHabitTemplateUseCase {

    private IHabitRepository habitRepository;
    private IHabitTemplateRepository habitTemplateRepository;

    @Override
    public void createHabitTemplate(Habit habit) {

        List<Habit> habits = habitRepository.findByName(habit.getName());
        int habitCount = habits.size();

        if (!habitTemplateRepository.existsByName(habit.getName()) && habitCount > 3) {
            HabitTemplate habitTemplate = HabitTemplate.builder()
                    .name(habit.getName())
                    .popularity(habitCount)
                    .build();
            habitTemplateRepository.save(habitTemplate);
        }
    }
}