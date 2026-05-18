package org.solen.business.exceptions;

public class UserNotFoundByEmailException extends RuntimeException {
    public UserNotFoundByEmailException(String email) {
        super("User with email " + email + " not found");
    }
}
