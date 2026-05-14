package org.example.habit_tracker.business.habitprogress;

import org.example.habit_tracker.domain.progress.HabitProgress;

import java.time.LocalDate;
import java.util.List;

public interface IGetProgressForUserUseCase {
    List<HabitProgress> getProgressForUser(Long userId, LocalDate from, LocalDate to);
}