package org.example.habit_tracker.business.noteusecases.fypstrategy;

import org.example.habit_tracker.domain.notes.Note;

import java.util.List;

public interface IRecommendationStrategy {
    List<Note> findPublicNotes(Long userId);
}