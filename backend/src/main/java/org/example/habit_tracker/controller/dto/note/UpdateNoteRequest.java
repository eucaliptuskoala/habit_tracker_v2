package org.example.habit_tracker.controller.dto.note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNoteRequest {
    private String title;
    private String content;
    private int personalFeeling;
    private Boolean isPublic;
}
