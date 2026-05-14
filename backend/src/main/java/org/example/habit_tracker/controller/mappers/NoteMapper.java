package org.example.habit_tracker.controller.mappers;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.controller.dto.note.NoteDto;
import org.example.habit_tracker.domain.notes.Note;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NoteMapper {

    private UserMapper userMapper;
    private HabitMapper habitMapper;

    public NoteDto convertToDto(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .personalFeeling(note.getPersonalFeeling())
                .isPublic(note.getIsPublic())
                .creator(userMapper.convertToDto(note.getCreator()))
                .habit(habitMapper.convertToDto(note.getHabit()))
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }
}