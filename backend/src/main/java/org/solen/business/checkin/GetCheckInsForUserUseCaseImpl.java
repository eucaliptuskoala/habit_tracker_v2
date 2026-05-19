package org.solen.business.checkin;

import lombok.AllArgsConstructor;
import org.solen.business.repos.ICheckInRepository;
import org.solen.domain.checkin.CheckIn;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class GetCheckInsForUserUseCaseImpl implements IGetCheckInsForUserUseCase {

    private ICheckInRepository checkInRepository;
    private CheckInTimelineBuilder timelineBuilder;

    @Override
    public List<CheckIn> getCheckInsForUser(Long userId, LocalDate from, LocalDate to) {
        List<CheckIn> raw = from != null && to != null
                ? checkInRepository.findCheckInsForUser(userId, from, to)
                : checkInRepository.findByHabitCreatorId(userId);
        return timelineBuilder.buildTimeline(raw);
    }

    @Override
    public Set<Long> findHabitIdsCheckedInTodayByUserId(Long userId) {
        return checkInRepository.findHabitIdsCheckedInTodayByUserId(userId);
    }
}
