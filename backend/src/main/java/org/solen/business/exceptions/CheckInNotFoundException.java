package org.solen.business.exceptions;

public class CheckInNotFoundException extends RuntimeException {
    public CheckInNotFoundException(Long id) {
        super("Check-in with id " + id + " not found");
    }
}
