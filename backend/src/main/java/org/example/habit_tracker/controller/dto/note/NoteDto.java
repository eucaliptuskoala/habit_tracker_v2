package org.example.habit_tracker.controller.dto.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.habit_tracker.controller.dto.habit.HabitDto;
import org.example.habit_tracker.controller.dto.user.UserDto;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
    private Long id;
    private String title;
    private String content;
    private int personalFeeling;
    private Boolean isPublic;
    private UserDto creator;
    private HabitDto habit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}