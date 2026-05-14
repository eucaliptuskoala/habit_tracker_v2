package org.example.habit_tracker.controller.mappers;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.controller.dto.habit.HabitDto;
import org.example.habit_tracker.domain.habits.Habit;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HabitMapper {

    private HabitTemplateMapper templateMapper;

    public HabitDto convertToDto(Habit habit) {
        return HabitDto.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .streak(habit.getStreak())
                .lastUpdatedStreak(habit.getLastUpdatedStreak())
                .thresholdDays(habit.getThresholdDays())
                .template(templateMapper.convertToDto(habit.getTemplate()))
                .build();
    }
}