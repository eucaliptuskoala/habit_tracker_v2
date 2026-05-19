package org.solen.business.checkin;

import lombok.AllArgsConstructor;
import org.solen.business.exceptions.HabitNotFoundByIdException;
import org.solen.business.repos.ICheckInRepository;
import org.solen.business.repos.IHabitRepository;
import org.solen.domain.checkin.CheckIn;
import org.solen.domain.checkin.Mood;
import org.solen.domain.habits.Habit;
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
        return checkInRepository.save(CheckIn.builder()
                .habit(habit)
                .date(habit.getLastUpdatedStreak().toLocalDate())
                .streakValue(habit.getStreak())
                .content(content)
                .isPublic(isPublic)
                .mood(mood)
                .createdAt(LocalDateTime.now())
                .build());
    }
}
