package org.example.habit_tracker.business.checkin;

import org.example.habit_tracker.domain.checkin.CheckIn;
import org.example.habit_tracker.domain.checkin.Mood;

public interface IUpdateCheckInUseCase {
    CheckIn update(Long id, String content, boolean isPublic, Mood mood);
}
