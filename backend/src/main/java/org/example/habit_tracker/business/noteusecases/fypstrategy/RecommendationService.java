package org.example.habit_tracker.business.noteusecases.fypstrategy;

import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.notes.Note;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    @Qualifier("habitNameBased")
    private final IRecommendationStrategy habitNameBased;

    @Qualifier("default")
    private final IRecommendationStrategy defaultStrategy;

    private final IHabitRepository habitRepository;

    public RecommendationService(
            @Qualifier("habitNameBased") IRecommendationStrategy habitNameBased,
            @Qualifier("default") IRecommendationStrategy defaultStrategy,
            IHabitRepository habitRepository
    ) {
        this.habitNameBased = habitNameBased;
        this.defaultStrategy = defaultStrategy;
        this.habitRepository = habitRepository;
    }

    public List<Note> findPublicNotes(Long userId) {
        List<Habit> userHabits = habitRepository.findByCreatorId(userId);

        if(userHabits.isEmpty()){
            return defaultStrategy.findPublicNotes(userId);
        }
        else{
            return habitNameBased.findPublicNotes(userId);
        }
    }
}