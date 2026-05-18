package org.solen.business.habitcases;

import lombok.AllArgsConstructor;
import org.solen.business.repos.IHabitRepository;
import org.solen.domain.habits.Habit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetHabitsByUserUseCaseImpl implements IGetHabitsByUserUseCase {

    private IHabitRepository repository;
    private StreakValidator validator;

    @Override
    public List<Habit> getHabitsByUser(Long userId) {
        List<Habit> habits = repository.findByCreatorId(userId);
        for(Habit habit : habits){
            validator.validateStreak(habit);
        }
        return habits;
    }
}