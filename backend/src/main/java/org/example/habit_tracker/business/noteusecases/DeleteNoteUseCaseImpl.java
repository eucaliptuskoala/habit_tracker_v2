package org.example.habit_tracker.business.noteusecases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteNoteUseCaseImpl implements  IDeleteNoteUseCase {

    private INoteRepository noteRepository;

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
