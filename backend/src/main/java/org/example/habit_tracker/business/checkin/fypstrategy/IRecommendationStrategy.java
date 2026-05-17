package org.example.habit_tracker.business.checkin.fypstrategy;

import org.example.habit_tracker.domain.checkin.CheckIn;

import java.util.List;

public interface IRecommendationStrategy {
    List<CheckIn> findPublicCheckIns(Long userId);
}
