package org.example.habit_tracker.business.noteusecases;

import org.example.habit_tracker.controller.dto.note.CreateNoteRequest;
import org.example.habit_tracker.domain.notes.Note;

public interface ICreateNoteUseCase {
    Note createNote(CreateNoteRequest createNoteRequest,  Long userId);
}