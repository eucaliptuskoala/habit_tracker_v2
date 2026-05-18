package org.example.habit_tracker.business.checkin;

import org.example.habit_tracker.domain.checkin.CheckIn;

import java.time.LocalDate;
import java.util.List;

public interface IGetCheckInsForUserUseCase {
    List<CheckIn> getCheckInsForUser(Long userId, LocalDate from, LocalDate to);
}
