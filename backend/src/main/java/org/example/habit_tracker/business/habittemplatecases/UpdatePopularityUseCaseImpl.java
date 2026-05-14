package org.example.habit_tracker.business.habittemplatecases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IHabitTemplateRepository;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdatePopularityUseCaseImpl implements IUpdatePopularityUseCase {

    private IHabitTemplateRepository repository;

    @Override
    public HabitTemplate updatePopularity(Long id) {
        HabitTemplate templateToUpdate = repository.findById(id);
        templateToUpdate.setPopularity(templateToUpdate.getPopularity() + 1);
        return repository.save(templateToUpdate);
    }
}