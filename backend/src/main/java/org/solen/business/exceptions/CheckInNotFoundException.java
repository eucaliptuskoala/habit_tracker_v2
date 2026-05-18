package org.example.habit_tracker.business.exceptions;

public class CheckInNotFoundException extends RuntimeException {
    public CheckInNotFoundException(Long id) {
        super("Check-in with id " + id + " not found");
    }
}
