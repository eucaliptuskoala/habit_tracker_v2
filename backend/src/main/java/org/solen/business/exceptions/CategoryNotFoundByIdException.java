package org.example.habit_tracker.business.exceptions;

public class CategoryNotFoundByIdException extends RuntimeException {

    public CategoryNotFoundByIdException(Long id) {
        super("Category with id " + id + " does not exist");
    }
}
