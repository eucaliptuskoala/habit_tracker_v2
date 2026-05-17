package org.example.habit_tracker.controller.dto.checkin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.habit_tracker.controller.dto.habit.HabitDto;
import org.example.habit_tracker.domain.checkin.Mood;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckInDto {
    private Long id;
    private HabitDto habit;
    private LocalDate date;
    private int streakValue;
    private String content;
    private boolean isPublic;
    private Mood mood;
    private LocalDateTime createdAt;
}
