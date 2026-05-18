package org.example.habit_tracker.business.checkin;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.HabitNotFoundByIdException;
import org.example.habit_tracker.business.repos.ICheckInRepository;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.domain.checkin.CheckIn;
import org.example.habit_tracker.domain.checkin.Mood;
import org.example.habit_tracker.domain.habits.Habit;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateCheckInUseCaseImpl implements ICreateCheckInUseCase {

    private ICheckInRepository checkInRepository;
    private IHabitRepository habitRepository;

    @Override
    public CheckIn create(Long habitId) {
        return createWithDetails(habitId, null, false, null);
    }

    @Override
    public CheckIn createWithDetails(Long habitId, String content, boolean isPublic, Mood mood) {
        Habit habit = habitRepository.findById(habitId);
        if (habit == null) {
            throw new HabitNotFoundByIdException(habitId);
        }
        CheckIn checkIn = CheckIn.builder()
                .habit(habit)
                .date(habit.getLastUpdatedStreak().toLocalDate())
                .streakValue(habit.getStreak())
                .content(content)
                .isPublic(isPublic)
                .mood(mood)
                .createdAt(LocalDateTime.now())
                .build();

        return checkInRepository.save(checkIn);
    }
}
