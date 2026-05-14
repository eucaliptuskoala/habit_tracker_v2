package org.example.habit_tracker.domain.habits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HabitTemplate {
    private Long id;
    private String name;
    private int popularity;
}
