package org.example.habit_tracker.controller.mappers;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.controller.dto.progress.HabitProgressDto;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class HabitProgressMapper {

    private HabitMapper habitMapper;

    public HabitProgressDto convertToDto(HabitProgress progress) {
        if(progress == null){ return null;}
        return HabitProgressDto.builder()
                .id(progress.getId())
                .habit(habitMapper.convertToDto(progress.getHabit()))
                .date(progress.getDate())
                .streakValue(progress.getStreakValue())
                .createdAt(progress.getCreatedAt())
                .build();
    }
}