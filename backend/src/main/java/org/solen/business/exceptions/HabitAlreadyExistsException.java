package org.solen.business.exceptions;

public class HabitAlreadyExistsException extends RuntimeException {
    public HabitAlreadyExistsException() {
        super("Habit Already Exists!");
    }
}
