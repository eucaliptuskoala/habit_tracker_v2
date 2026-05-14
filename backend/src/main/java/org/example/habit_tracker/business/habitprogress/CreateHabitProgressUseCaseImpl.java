package org.example.habit_tracker.business.habitprogress;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.HabitNotFoundByIdException;
import org.example.habit_tracker.business.repos.IHabitProgressRepository;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateHabitProgressUseCaseImpl implements ICreateHabitProgressUseCase {

    private IHabitProgressRepository habitProgressRepository;
    private IHabitRepository habitRepository;

    @Override
    public HabitProgress create(Long habitId) {
        Habit habit = habitRepository.findById(habitId);
        if (habit == null){
            throw new HabitNotFoundByIdException(habitId);
        }
        HabitProgress progress = HabitProgress.builder()
                .habit(habit)
                .date(habit.getLastUpdatedStreak().toLocalDate())
                .streakValue(habit.getStreak())
                .createdAt(LocalDateTime.now())
                .build();

        return habitProgressRepository.save(progress);
    }
}