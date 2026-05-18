package org.example.habit_tracker.business.repos;

import org.example.habit_tracker.domain.checkin.CheckIn;

import java.time.LocalDate;
import java.util.List;

public interface ICheckInRepository {
    CheckIn save(CheckIn checkIn);
    CheckIn findById(Long id);
    List<CheckIn> findByHabitCreatorId(Long userId);
    List<CheckIn> findCheckInsForUser(Long userId, LocalDate from, LocalDate to);
    List<CheckIn> findPublicCheckIns();
    boolean findByCheckInIdAndEmail(Long checkInId, String email);
    void deleteById(Long id);
}
