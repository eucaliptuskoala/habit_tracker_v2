package org.example.habit_tracker.business.checkin;

import org.example.habit_tracker.domain.checkin.CheckIn;
import org.example.habit_tracker.domain.checkin.Mood;

public interface ICreateCheckInUseCase {
    CheckIn create(Long habitId);
    CheckIn createWithDetails(Long habitId, String content, boolean isPublic, Mood mood);
}
