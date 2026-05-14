package org.example.habit_tracker.persistence.converters;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.domain.notes.Note;
import org.example.habit_tracker.persistence.entities.NoteEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NoteConverter {

    private UserConverter userConverter;
    private HabitConverter habitConverter;

    public NoteEntity convertToEntity(Note note) {
        return NoteEntity.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .personalFeeling(note.getPersonalFeeling())
                .isPublic(note.getIsPublic())
                .creator(userConverter.convertToEntity(note.getCreator()))
                .habit(habitConverter.convertToEntity(note.getHabit()))
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }

    public Note convertToDomain(NoteEntity noteEntity) {
        return Note.builder()
                .id(noteEntity.getId())
                .title(noteEntity.getTitle())
                .content(noteEntity.getContent())
                .personalFeeling(noteEntity.getPersonalFeeling())
                .isPublic(noteEntity.getIsPublic())
                .creator(userConverter.convertToDomain(noteEntity.getCreator()))
                .habit(habitConverter.convertToDomain(noteEntity.getHabit()))
                .createdAt(noteEntity.getCreatedAt())
                .updatedAt(noteEntity.getUpdatedAt())
                .build();
    }
}