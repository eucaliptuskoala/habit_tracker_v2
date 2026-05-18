package org.example.habit_tracker.business.repos;

import org.example.habit_tracker.domain.habits.Habit;

import java.util.List;

public interface IHabitRepository {
    Habit save(Habit habit);
    Habit findById(Long id);
    List<Habit> findAll();
    List<Habit> findByName(String name);
    List<Habit> findByCreatorId(Long userId);
    void deleteById(Long id);
    boolean existsByHabitIdAndCreatorEmail(Long habitId, String email);
}