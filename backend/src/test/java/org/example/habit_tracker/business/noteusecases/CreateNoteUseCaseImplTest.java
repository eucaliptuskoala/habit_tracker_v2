package org.example.habit_tracker.business.noteusecases;

import org.example.habit_tracker.business.exceptions.HabitNotFoundByIdException;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.controller.dto.note.CreateNoteRequest;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.notes.Note;
import org.example.habit_tracker.domain.users.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateNoteUseCaseImplTest {

    @Mock
    private INoteRepository noteRepository;

    @Mock
    private IHabitRepository habitRepository;

    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private CreateNoteUseCaseImpl createNoteUseCaseImpl;

    CreateNoteRequest createNoteRequest;
    Long userId;
    User user;
    Habit habit;

    @BeforeEach
    void setup() {
        userId = 1L;

        createNoteRequest = CreateNoteRequest.builder()
                .title("title")
                .content("content")
                .personalFeeling(1)
                .isPublic(false)
                .habitId(1L)
                .build();

        user = User.builder()
                .id(1L)
                .name("name")
                .email("email")
                .password("password")
                .isAdmin(false)
                .build();

        habit = Habit.builder()
                .id(1L)
                .name("name")
                .description("description")
                .streak(0)
                .lastUpdatedStreak(null)
                .thresholdDays(2)
                .creator(user)
                .template(null)
                .build();
    }

    @Test
    void createNote_success() {
        when(userRepository.findById(userId)).thenReturn(user);
        when(habitRepository.findById(createNoteRequest.getHabitId())).thenReturn(habit);

        createNoteUseCaseImpl.createNote(createNoteRequest, userId);

        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void createNote_request_is_null(){
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()->
            createNoteUseCaseImpl.createNote(null, userId));
        assertEquals("Request cannot be null", exception.getMessage());
        verify(noteRepository, never()).save(any(Note.class));
    }

    @Test
    void createNote_user_not_found(){
        when(userRepository.findById(userId)).thenReturn(null);
        assertThrows(UserNotFoundByIdException.class,() -> createNoteUseCaseImpl.createNote(createNoteRequest, userId));
        verify(noteRepository, never()).save(any(Note.class));
    }

    @Test
    void createNote_habit_not_found(){
        when(userRepository.findById(userId)).thenReturn(user);
        when(habitRepository.findById(createNoteRequest.getHabitId())).thenReturn(null);
        assertThrows(HabitNotFoundByIdException.class, () -> createNoteUseCaseImpl.createNote(createNoteRequest, userId));
        verify(noteRepository, never()).save(any(Note.class));
    }
}