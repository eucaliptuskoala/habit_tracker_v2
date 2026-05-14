package org.example.habit_tracker.business.noteusecases;

import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.noteusecases.fypstrategy.RecommendationService;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.notes.Note;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetForYouNotesUseCaseImplTest {

    @Mock
    private RecommendationService recommendationService;

    @Mock
    private IUserRepository userRepository;

    private GetForYouNotesUseCaseImpl testUseCase;

    Long userId;
    User user;
    List<Note> notes;

    @BeforeEach
    void setUp() {
        testUseCase = new GetForYouNotesUseCaseImpl(recommendationService, userRepository);

        userId = 1L;
        notes = new ArrayList<>();

        Note note1 = Note.builder().build();
        notes.add(note1);
        Note note2 = Note.builder().build();
        notes.add(note2);

        user = User.builder()
                .id(1L)
                .name("name")
                .email("email")
                .password("password")
                .isAdmin(false)
                .build();
    }

    @Test
    void getForYouNotes_success() {
        when(userRepository.existsById(userId)).thenReturn(true);
        when(recommendationService.findPublicNotes(userId)).thenReturn(notes);

        List<Note> result = testUseCase.getForYouNotes(userId);
        verify(recommendationService, times(1)).findPublicNotes(userId);
        assertEquals(notes, result);
    }

    @Test
    void getForYouNotes_userNotFound() {
        when(userRepository.existsById(userId)).thenReturn(false);
        UserNotFoundByIdException exception = assertThrows(UserNotFoundByIdException.class, () -> testUseCase.getForYouNotes(userId));
        assertEquals("User with id 1 does not exist", exception.getMessage());
        verify(userRepository, times(1)).existsById(userId);
        verify(recommendationService, never()).findPublicNotes(userId);

    }
}