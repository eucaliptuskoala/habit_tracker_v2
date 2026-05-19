package org.solen.business.habitcases;

import lombok.AllArgsConstructor;
import org.solen.business.exceptions.StreakAlreadyUpdatedException;
import org.solen.business.checkin.ICreateCheckInUseCase;
import org.solen.business.repos.IHabitRepository;
import org.solen.domain.habits.Habit;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

// The primary "mark habit as done today" use case.
// Flow:
//   1. Validate streak hasn't expired (StreakValidator resets to 0 if gap > threshold)
//   2. Guard: only one update per day per habit (StreakAlreadyUpdatedException)
//   3. Increment streak, update lastUpdatedStreak, save
//   4. Auto-create a daily CheckIn snapshotting the new streak value
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

        // Reset to 0 if too many days have passed since last update
        streakValidator.validateStreak(habit);

        // Prevent double-tap on the same day
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
        // Create a check-in record for today with the streak value snapshotted
        createCheckIn.create(updated.getId());

        return updated;
    }
}