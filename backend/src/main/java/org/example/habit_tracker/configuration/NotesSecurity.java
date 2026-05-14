package org.example.habit_tracker.configuration;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.springframework.stereotype.Component;

@Component("noteSecurity")
@AllArgsConstructor
public class NotesSecurity {

    private INoteRepository noteRepository;

    public boolean isOwnerByEmail(Long noteId, String email) {
        return noteRepository.existsByNoteIdAndCreatorEmail(noteId, email);
    }
}