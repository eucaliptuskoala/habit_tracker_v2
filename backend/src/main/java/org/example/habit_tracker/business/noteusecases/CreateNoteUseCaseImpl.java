package org.example.habit_tracker.business.noteusecases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.HabitNotFoundByIdException;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.controller.dto.note.CreateNoteRequest;
import org.example.habit_tracker.domain.notes.Note;
import org.example.habit_tracker.domain.users.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class CreateNoteUseCaseImpl implements ICreateNoteUseCase {

    private INoteRepository noteRepository;
    private IUserRepository userRepository;
    private IHabitRepository habitRepository;

    @Override
    public Note createNote(CreateNoteRequest createNoteRequest, Long userId) {

        if(createNoteRequest == null){
            throw new IllegalArgumentException("Request cannot be null");
        }

        User user = userRepository.findById(userId);

        if(user==null){
            throw new UserNotFoundByIdException(userId);
        }

        Habit habit = habitRepository.findById(createNoteRequest.getHabitId());

        if(habit==null){
            throw new HabitNotFoundByIdException(createNoteRequest.getHabitId());
        }

        Note note = Note.builder()
                .title(createNoteRequest.getTitle())
                .content(createNoteRequest.getContent())
                .personalFeeling(createNoteRequest.getPersonalFeeling())
                .isPublic(createNoteRequest.getIsPublic())
                .creator(user)
                .habit(habit)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return noteRepository.save(note);
    }
}