package org.example.habit_tracker.controller.mappers;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.controller.dto.checkin.CheckInDto;
import org.example.habit_tracker.domain.checkin.CheckIn;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckInMapper {

    private HabitMapper habitMapper;

    public CheckInDto convertToDto(CheckIn checkIn) {
        if (checkIn == null) { return null; }
        return CheckInDto.builder()
                .id(checkIn.getId())
                .habit(habitMapper.convertToDto(checkIn.getHabit()))
                .date(checkIn.getDate())
                .streakValue(checkIn.getStreakValue())
                .content(checkIn.getContent())
                .isPublic(checkIn.isPublic())
                .mood(checkIn.getMood())
                .createdAt(checkIn.getCreatedAt())
                .build();
    }
}
