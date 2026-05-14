package org.example.habit_tracker.controller.dto.habit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HabitDto {
    private Long id;
    private String name;
    private String description;
    private int streak;
    private LocalDateTime lastUpdatedStreak;
    private int thresholdDays;
    private HabitTemplateDto template;
}