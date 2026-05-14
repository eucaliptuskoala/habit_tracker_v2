package org.example.habit_tracker.business.noteusecases;

import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.example.habit_tracker.business.repos.IUserRepository;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetNotesByUserUseCaseImplTest {

    @Mock
    private INoteRepository noteRepository;
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private GetNotesByUserUseCaseImpl getNotesByUserUseCaseImpl;

    Long userId;
    User user;
    Habit habit;
    List<Note> notes;

    @BeforeEach
    void setUp() {
        userId = 1L;

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

        notes = new ArrayList<>();

        Note note1 = Note.builder()
                .id(1L)
                .title("title")
                .content("content")
                .personalFeeling(1)
                .creator(user)
                .habit(habit)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();

        notes.add(note1);

        Note note2 = Note.builder()
                .id(2L)
                .title("title")
                .content("content")
                .personalFeeling(2)
                .creator(user)
                .habit(habit)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();

        notes.add(note2);

        Note note3 = Note.builder()
                .id(3L)
                .title("title")
                .content("content")
                .personalFeeling(3)
                .creator(user)
                .habit(habit)
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .build();

        notes.add(note3);
    }

    @Test
    void getNotesByUser_success(){
        when(userRepository.findById(userId)).thenReturn(user);
        when(noteRepository.findByCreatorId(userId)).thenReturn(notes);

        getNotesByUserUseCaseImpl.getNotesByUser(userId);

        assertNotNull(notes);
        assertEquals(3,  notes.size());
    }

    @Test
    void getNotesByUser_userNotFoundById(){
        when(userRepository.findById(userId)).thenReturn(null);
        UserNotFoundByIdException exception = assertThrows(UserNotFoundByIdException.class, () -> getNotesByUserUseCaseImpl.getNotesByUser(userId));
        assertEquals("User with id 1 does not exist", exception.getMessage());
        verify(noteRepository, never()).findByCreatorId(userId);
    }
}