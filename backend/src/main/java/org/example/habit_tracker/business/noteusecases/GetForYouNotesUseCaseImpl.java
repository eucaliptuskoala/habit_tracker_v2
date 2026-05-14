package org.example.habit_tracker.business.noteusecases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.noteusecases.fypstrategy.RecommendationService;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.notes.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetForYouNotesUseCaseImpl implements IGetForYouNotesUseCase {

    private RecommendationService recommendationService;

    private IUserRepository userRepository;

    @Override
    public List<Note> getForYouNotes(Long userId) {
        if(!userRepository.existsById(userId)) {
            throw new UserNotFoundByIdException(userId);
        }
        return recommendationService.findPublicNotes(userId);
    }
}