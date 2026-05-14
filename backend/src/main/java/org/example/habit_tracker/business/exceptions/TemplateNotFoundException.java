package org.example.habit_tracker.business.exceptions;

public class TemplateNotFoundException extends RuntimeException {

    public TemplateNotFoundException(Long templateId) {
        super("Template with id " + templateId + " does not exist");
    }
}
