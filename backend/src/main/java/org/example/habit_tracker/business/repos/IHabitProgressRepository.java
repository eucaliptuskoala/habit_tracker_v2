package org.example.habit_tracker.business.repos;

import org.example.habit_tracker.domain.progress.HabitProgress;

import java.time.LocalDate;
import java.util.List;

public interface IHabitProgressRepository {
    HabitProgress save(HabitProgress progress);
    List<HabitProgress> findByHabitCreatorId(Long userId);
    List<HabitProgress> findProgressForUser(Long userId, LocalDate from, LocalDate to);
}
