package org.example.habit_tracker.controller;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.noteusecases.*;
import org.example.habit_tracker.configuration.UserIdProvider;
import org.example.habit_tracker.controller.dto.note.CreateNoteRequest;
import org.example.habit_tracker.controller.dto.note.NoteDto;
import org.example.habit_tracker.controller.dto.note.UpdateNoteRequest;
import org.example.habit_tracker.controller.mappers.NoteMapper;
import org.example.habit_tracker.domain.notes.Note;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@AllArgsConstructor
public class NoteController {

    private ICreateNoteUseCase createNoteUseCase;
    private IGetNotesByUserUseCase getNotesByUserUseCase;
    private IUpdateNoteUseCase updateNoteUseCase;
    private IDeleteNoteUseCase deleteNoteUseCase;
    private IGetForYouNotesUseCase getForYouNotesUseCase;
    private NoteMapper noteMapper;
    private UserIdProvider userIdProvider;

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody CreateNoteRequest createNoteRequest) {
        Long userId = userIdProvider.getUserId();
        Note note = createNoteUseCase.createNote(createNoteRequest, userId);
        return ResponseEntity.ok().body(noteMapper.convertToDto(note));
    }

    @GetMapping("/my")
    public ResponseEntity<List<NoteDto>> getMyNotes() {
        Long userId = userIdProvider.getUserId();
        List<Note> notes = getNotesByUserUseCase.getNotesByUser(userId);
        return ResponseEntity.ok().body(notes.stream().map(noteMapper::convertToDto).toList());
    }

    @PutMapping("/{id}")
    @PreAuthorize("@noteSecurity.isOwnerByEmail(#id, authentication.name)")
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long id, @RequestBody UpdateNoteRequest request) {
        Note note = updateNoteUseCase.updateNote(request, id);
        return ResponseEntity.ok().body(noteMapper.convertToDto(note));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@noteSecurity.isOwnerByEmail(#id, authentication.name)")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        deleteNoteUseCase.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fyp")
    public ResponseEntity<List<NoteDto>> getFypNotes() {
        Long userId = userIdProvider.getUserId();
        List<Note> forYouNotes = getForYouNotesUseCase.getForYouNotes(userId);
        return ResponseEntity.ok().body(forYouNotes.stream().map(noteMapper::convertToDto).toList());
    }
}