package org.example.habit_tracker.domain.notes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.users.User;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    private Long id;
    private String title;
    private String content;
    private int personalFeeling;
    private Boolean isPublic;
    private User creator;
    private Habit habit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}