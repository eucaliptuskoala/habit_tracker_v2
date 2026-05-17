package org.example.habit_tracker.domain.habits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.habit_tracker.domain.users.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Habit {
    private Long id;
    private String name;
    private String description;
    private int streak;
    private LocalDateTime lastUpdatedStreak;
    private int thresholdDays;
    private Category category;
    private User creator;
}