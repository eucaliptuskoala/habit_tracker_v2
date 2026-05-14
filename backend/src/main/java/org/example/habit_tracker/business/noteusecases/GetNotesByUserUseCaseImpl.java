package org.example.habit_tracker.business.noteusecases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.notes.Note;
import org.example.habit_tracker.domain.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetNotesByUserUseCaseImpl implements  IGetNotesByUserUseCase {

    private INoteRepository noteRepository;
    private IUserRepository userRepository;

    @Override
    public List<Note> getNotesByUser(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundByIdException(userId);
        }
        return noteRepository.findByCreatorId(user.getId());
    }
}