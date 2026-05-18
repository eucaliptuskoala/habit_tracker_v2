package org.example.habit_tracker.controller.dto.habit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateHabitRequest {
    private Long categoryId;
    private String name;
    private String description;
}
