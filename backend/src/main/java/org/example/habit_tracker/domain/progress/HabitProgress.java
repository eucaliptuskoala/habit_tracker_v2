package org.example.habit_tracker.domain.progress;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.habit_tracker.domain.habits.Habit;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HabitProgress {
    private Long id;
    private Habit habit;
    private LocalDate date;
    private int streakValue;
    private LocalDateTime createdAt;
}