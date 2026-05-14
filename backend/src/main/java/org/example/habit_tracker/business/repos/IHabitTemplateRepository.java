package org.example.habit_tracker.business.repos;

import org.example.habit_tracker.domain.habits.HabitTemplate;

import java.util.List;

public interface IHabitTemplateRepository {
    boolean existsByName(String name);
    HabitTemplate save(HabitTemplate template);
    HabitTemplate findById(Long id);
    List<HabitTemplate> findAll();
    void deleteById(Long id);
}