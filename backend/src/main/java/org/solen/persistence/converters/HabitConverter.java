package org.example.habit_tracker.persistence.converters;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.persistence.entities.HabitEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HabitConverter {

    private UserConverter userConverter;
    private CategoryConverter categoryConverter;

    public HabitEntity convertToEntity(Habit habit) {
        return HabitEntity.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .streak(habit.getStreak())
                .lastUpdatedStreak(habit.getLastUpdatedStreak())
                .thresholdDays(habit.getThresholdDays())
                .category(categoryConverter.convertToEntity(habit.getCategory()))
                .creator(userConverter.convertToEntity(habit.getCreator()))
                .build();
    }

    public Habit convertToDomain(HabitEntity entity) {
        return Habit.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .streak(entity.getStreak())
                .lastUpdatedStreak(entity.getLastUpdatedStreak())
                .thresholdDays(entity.getThresholdDays())
                .category(categoryConverter.convertToDomain(entity.getCategory()))
                .creator(userConverter.convertToDomain(entity.getCreator()))
                .build();
    }
}