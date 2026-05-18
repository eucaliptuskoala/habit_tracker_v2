package org.solen.business.checkin;

import org.solen.domain.checkin.CheckIn;
import org.solen.domain.checkin.Mood;

public interface ICreateCheckInUseCase {
    CheckIn create(Long habitId);
    CheckIn createWithDetails(Long habitId, String content, boolean isPublic, Mood mood);
}
