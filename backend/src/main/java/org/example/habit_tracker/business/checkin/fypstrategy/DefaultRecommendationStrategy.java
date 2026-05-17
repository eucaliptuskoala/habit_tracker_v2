package org.example.habit_tracker.business.checkin.fypstrategy;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.ICheckInRepository;
import org.example.habit_tracker.domain.checkin.CheckIn;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("default")
@AllArgsConstructor
public class DefaultRecommendationStrategy implements IRecommendationStrategy {

    private ICheckInRepository checkInRepository;

    @Override
    public List<CheckIn> findPublicCheckIns(Long userId) {
        return checkInRepository.findPublicCheckIns();
    }
}
