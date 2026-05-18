package org.solen.business.checkin;

import org.solen.domain.checkin.CheckIn;

import java.time.LocalDate;
import java.util.List;

public interface IGetCheckInsForUserUseCase {
    List<CheckIn> getCheckInsForUser(Long userId, LocalDate from, LocalDate to);
}
