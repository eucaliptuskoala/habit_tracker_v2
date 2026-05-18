package org.example.habit_tracker.business.exceptions;

public class UserNotFoundByEmailException extends RuntimeException {
    public UserNotFoundByEmailException(String email) {
        super("User with email " + email + " not found");
    }
}
