package org.example.habit_tracker.business.checkin.fypstrategy;

import org.example.habit_tracker.domain.checkin.CheckIn;

import java.util.List;

public interface IGetForYouCheckInsUseCase {
    List<CheckIn> getForYouCheckIns(Long userId);
}
