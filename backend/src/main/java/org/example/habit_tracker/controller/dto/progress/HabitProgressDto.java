package org.example.habit_tracker.controller.dto.progress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.habit_tracker.controller.dto.habit.HabitDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HabitProgressDto {
    private Long id;
    private HabitDto habit;
    private LocalDate date;
    private int streakValue;
    private LocalDateTime createdAt;
}