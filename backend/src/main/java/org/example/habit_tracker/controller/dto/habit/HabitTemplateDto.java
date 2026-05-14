package org.example.habit_tracker.controller.dto.habit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HabitTemplateDto {
    private Long id;
    private String name;
    private int popularity;
}