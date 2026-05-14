package org.example.habit_tracker.business.noteusecases;

import org.example.habit_tracker.controller.dto.note.UpdateNoteRequest;
import org.example.habit_tracker.domain.notes.Note;

public interface IUpdateNoteUseCase {
    Note updateNote(UpdateNoteRequest request, Long id);
}
