package org.example.habit_tracker.business.noteusecases;

import org.example.habit_tracker.domain.notes.Note;

import java.util.List;

public interface IGetNotesByUserUseCase {
    List<Note> getNotesByUser(Long userId);
}