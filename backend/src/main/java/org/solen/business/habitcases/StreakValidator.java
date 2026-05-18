package org.solen.business.habitcases;

import lombok.AllArgsConstructor;
import org.solen.business.repos.IHabitRepository;
import org.solen.domain.habits.Habit;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class StreakValidator {

    private IHabitRepository repository;

    public void validateStreak(Habit habit) {

        if (habit.getLastUpdatedStreak() == null) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        long daysSinceLast = Duration.between(habit.getLastUpdatedStreak(), now).toDays();

        if (daysSinceLast > habit.getThresholdDays()) {
            habit.setStreak(0);
            repository.save(habit);
        }
    }
}