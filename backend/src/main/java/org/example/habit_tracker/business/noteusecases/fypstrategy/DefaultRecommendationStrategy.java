package org.example.habit_tracker.business.noteusecases.fypstrategy;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.example.habit_tracker.domain.notes.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("default")
@AllArgsConstructor
public class DefaultRecommendationStrategy implements IRecommendationStrategy{

    private INoteRepository noteRepository;

    @Override
    public List<Note> findPublicNotes(Long userId) { return noteRepository.findPublicNotes(); }
}