package org.solen.controller.mappers;

import org.solen.controller.dto.habit.HabitDto;
import org.solen.domain.habits.Habit;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class HabitMapper {

    public HabitDto convertToDto(Habit habit) {
        return convertToDto(habit, Set.of());
    }

    public HabitDto convertToDto(Habit habit, Set<Long> checkedInTodayIds) {
        return HabitDto.builder()
                .id(habit.getId())
                .name(habit.getName())
                .description(habit.getDescription())
                .streak(habit.getStreak())
                .lastUpdatedStreak(habit.getLastUpdatedStreak())
                .thresholdDays(habit.getThresholdDays())
                .categoryId(habit.getCategory() != null ? habit.getCategory().getId() : null)
                .categoryName(habit.getCategory() != null ? habit.getCategory().getName() : null)
                .checkedInToday(checkedInTodayIds.contains(habit.getId()))
                .build();
    }
}