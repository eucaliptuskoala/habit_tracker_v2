package org.example.habit_tracker.business.habittemplatecases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IHabitTemplateRepository;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class GetPopularHabitTemplatesUseCaseImpl implements IGetPopularHabitTemplates {

    private IHabitTemplateRepository repository;

    @Override
    public List<HabitTemplate> getPopularHabitTemplates() {
        List<HabitTemplate> habitTemplates = repository.findAll();
        return habitTemplates.stream().sorted(Comparator.comparing(HabitTemplate::getPopularity).reversed()).limit(10).toList();
    }
}