package org.example.habit_tracker.business.noteusecases;

import org.example.habit_tracker.business.exceptions.NoteNotFoundByIdException;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.example.habit_tracker.controller.dto.note.UpdateNoteRequest;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.notes.Note;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateNoteUseCaseImplTest {

    @Mock
    private INoteRepository noteRepository;

    @InjectMocks
    private UpdateNoteUseCaseImpl updateNoteUseCaseImpl;

    Long noteId;
    User user;
    Habit habit;
    UpdateNoteRequest request;
    Note note;

    @BeforeEach
    void setUp() {
        noteId = 1L;

         user = User.builder()
                .name("user")
                .email("user")
                .password("password")
                .isAdmin(false)
                .build();

        habit = Habit.builder()
                .name("habit")
                .description("habit")
                .streak(0)
                .thresholdDays(2)
                .creator(user)
                .build();

        note = Note.builder()
                .id(1L)
                .title("title")
                .content("content")
                .personalFeeling(5)
                .isPublic(false)
                .creator(user)
                .habit(habit)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        request = UpdateNoteRequest.builder()
                .title("new title")
                .content("new content")
                .personalFeeling(7)
                .isPublic(true)
                .build();
    }

    @Test
    void updateNote_success() {
        when(noteRepository.findById(noteId)).thenReturn(note);
        when(noteRepository.save(note)).thenReturn(note);

        Note updated = updateNoteUseCaseImpl.updateNote(request, noteId);

        assertNotNull(updated);
        assertEquals(noteId, updated.getId());
        assertEquals(note.getTitle(), updated.getTitle());
        assertEquals(note.getContent(), updated.getContent());
    }

    @Test
    void updateNote_requestIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> updateNoteUseCaseImpl.updateNote(null, noteId));
        assertEquals("request is null", exception.getMessage());
        verify(noteRepository, never()).save(note);
    }

    @Test
    void updateNote_noteNotFoundById() {
        when(noteRepository.findById(noteId)).thenReturn(null);
        NoteNotFoundByIdException exception = assertThrows(NoteNotFoundByIdException.class, () -> updateNoteUseCaseImpl.updateNote(request, noteId));
        assertEquals("Note with id 1 does not exist", exception.getMessage());
        verify(noteRepository, never()).save(note);
    }
}