package org.solen.business.exceptions;

public class CategoryNotFoundByIdException extends RuntimeException {

    public CategoryNotFoundByIdException(Long id) {
        super("Category with id " + id + " does not exist");
    }
}
