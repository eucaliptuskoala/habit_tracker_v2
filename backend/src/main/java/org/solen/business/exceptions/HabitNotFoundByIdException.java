package org.example.habit_tracker.business.exceptions;

public class HabitNotFoundByIdException extends RuntimeException {

    public HabitNotFoundByIdException(Long id) {
        super("Habit with id " + id + " does not exist");
    }

}