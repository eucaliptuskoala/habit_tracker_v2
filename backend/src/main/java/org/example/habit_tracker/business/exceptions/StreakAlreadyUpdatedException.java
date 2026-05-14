package org.example.habit_tracker.business.exceptions;

public class StreakAlreadyUpdatedException extends RuntimeException {
    public StreakAlreadyUpdatedException() {
        super("Streak already updated!");
    }
}
