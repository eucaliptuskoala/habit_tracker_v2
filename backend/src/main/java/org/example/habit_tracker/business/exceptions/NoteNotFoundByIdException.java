package org.example.habit_tracker.business.exceptions;

public class NoteNotFoundByIdException extends RuntimeException {
    public NoteNotFoundByIdException(Long id) {
        super("Note with id " + id + " does not exist");
    }
}
