package org.example.habit_tracker.business.exceptions;

public class HabitAlreadyExistsException extends RuntimeException {
    public HabitAlreadyExistsException() {
        super("Habit Already Exists!");
    }
}
