package org.solen.business.exceptions;

public class UserNotFoundByIdException extends RuntimeException {

    public UserNotFoundByIdException(Long id) {
        super("User with id " + id + " does not exist");
    }

}