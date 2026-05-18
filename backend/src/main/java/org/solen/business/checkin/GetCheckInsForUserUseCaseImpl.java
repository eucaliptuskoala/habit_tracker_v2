package org.example.habit_tracker.business.checkin;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.ICheckInRepository;
import org.example.habit_tracker.domain.checkin.CheckIn;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class GetCheckInsForUserUseCaseImpl implements IGetCheckInsForUserUseCase {

    private ICheckInRepository checkInRepository;
    private CheckInTimelineBuilder timelineBuilder;

    @Override
    public List<CheckIn> getCheckInsForUser(Long userId, LocalDate from, LocalDate to) {
        List<CheckIn> raw;
        if (from != null && to != null) {
            raw = checkInRepository.findCheckInsForUser(userId, from, to);
        } else {
            raw = checkInRepository.findByHabitCreatorId(userId);
        }
        return timelineBuilder.buildTimeline(raw);
    }
}
