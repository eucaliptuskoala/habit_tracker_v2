package org.solen.business.exceptions;

public class StreakAlreadyUpdatedException extends RuntimeException {
    public StreakAlreadyUpdatedException() {
        super("Streak already updated!");
    }
}
