package org.solen.business.habitcases;

import lombok.AllArgsConstructor;
import org.solen.business.exceptions.StreakAlreadyUpdatedException;
import org.solen.business.checkin.ICreateCheckInUseCase;
import org.solen.business.repos.IHabitRepository;
import org.solen.domain.habits.Habit;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UpdateStreakUseCaseImpl implements IUpdateStreakUseCase {

    private IHabitRepository repository;
    private ICreateCheckInUseCase createCheckIn;
    private StreakValidator streakValidator;

    @Override
    public Habit updateStreak(Long id) {
        Habit habit = repository.findById(id);
        LocalDateTime now = LocalDateTime.now();

        streakValidator.validateStreak(habit);

        if(habit.getLastUpdatedStreak() != null){
            LocalDate last = habit.getLastUpdatedStreak().toLocalDate();
            LocalDate today = now.toLocalDate();

            if(last.equals(today)){
                throw new StreakAlreadyUpdatedException();
            }
        }

        habit.setStreak(habit.getStreak() + 1);
        habit.setLastUpdatedStreak(now);

        Habit updated = repository.save(habit);
        createCheckIn.create(updated.getId());

        return updated;
    }
}