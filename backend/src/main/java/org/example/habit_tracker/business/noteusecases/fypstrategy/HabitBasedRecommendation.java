package org.example.habit_tracker.business.noteusecases.fypstrategy;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.notes.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("habitNameBased")
@AllArgsConstructor
public class HabitBasedRecommendation implements IRecommendationStrategy {

    private INoteRepository noteRepository;
    private IHabitRepository habitRepository;

    @Override
    public List<Note> findPublicNotes(Long userId) {

        List<String> userHabitNames = habitRepository.findByCreatorId(userId).stream().map(Habit::getName).toList();

        return noteRepository.findPublicNotes().stream()
                .filter(note -> !note.getCreator().getId().equals(userId))
                .filter(note -> userHabitNames.contains(note.getHabit().getName()))
                .toList();
    }
}