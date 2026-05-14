package org.example.habit_tracker.business.repos;

import org.example.habit_tracker.domain.notes.Note;

import java.util.List;

public interface INoteRepository {
    Note save(Note note);
    boolean existsByNoteIdAndCreatorEmail(Long id, String email);
    List<Note> findByCreatorId(Long userId);
    Note findById(Long id);
    void deleteById(Long id);
    List<Note> findPublicNotes();
}