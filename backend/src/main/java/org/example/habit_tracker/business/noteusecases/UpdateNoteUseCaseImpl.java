package org.example.habit_tracker.business.noteusecases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.NoteNotFoundByIdException;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.example.habit_tracker.controller.dto.note.UpdateNoteRequest;
import org.example.habit_tracker.domain.notes.Note;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateNoteUseCaseImpl implements IUpdateNoteUseCase {

    private INoteRepository noteRepository;

    @Override
    public Note updateNote(UpdateNoteRequest request, Long id){

        if(request == null){
            throw new IllegalArgumentException("request is null");
        }

        Note noteToUpdate = noteRepository.findById(id);

        if(noteToUpdate == null){
            throw new NoteNotFoundByIdException(id);
        }

        noteToUpdate.setTitle(request.getTitle());
        noteToUpdate.setContent(request.getContent());
        noteToUpdate.setPersonalFeeling(request.getPersonalFeeling());
        noteToUpdate.setIsPublic(request.getIsPublic());

        return noteRepository.save(noteToUpdate);
    }
}