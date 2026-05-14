package org.example.habit_tracker.business.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Email " + email + " already exists");
    }

}