package org.example.habit_tracker.persistence.converters;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.example.habit_tracker.persistence.entities.HabitProgressEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HabitProgressConverter {

    private HabitConverter habitConverter;

    public HabitProgressEntity convertToEntity(HabitProgress habitProgress) {
        return HabitProgressEntity.builder()
                .id(habitProgress.getId())
                .habit(habitConverter.convertToEntity(habitProgress.getHabit()))
                .date(habitProgress.getDate())
                .streakValue(habitProgress.getStreakValue())
                .createdAt(habitProgress.getCreatedAt())
                .build();
    }

    public HabitProgress convertToDomain(HabitProgressEntity habitProgress) {
        return HabitProgress.builder()
                .id(habitProgress.getId())
                .habit(habitConverter.convertToDomain(habitProgress.getHabit()))
                .date(habitProgress.getDate())
                .streakValue(habitProgress.getStreakValue())
                .createdAt(habitProgress.getCreatedAt())
                .build();
    }
}