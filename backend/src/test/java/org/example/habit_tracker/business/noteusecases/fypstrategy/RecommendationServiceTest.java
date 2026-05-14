package org.example.habit_tracker.business.noteusecases.fypstrategy;

import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.notes.Note;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {
    @Mock
    private IRecommendationStrategy habitNameBased;

    @Mock
    private IRecommendationStrategy defaultStrategy;

    @Mock
    private IHabitRepository habitRepository;

    private RecommendationService recommendationService;

    Long userId;
    User user;
    Habit habit;
    List<Note> notes;

    @BeforeEach
    void setUp() {
        recommendationService = new RecommendationService(habitNameBased,  defaultStrategy, habitRepository);

        userId = 1L;

        notes = new ArrayList<>();

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

        notes.add(note1);
        notes.add(note2);
        notes.add(note3);
    }

    @Test
    void findPublicNotes_defaultStrategy() {
        when(habitRepository.findByCreatorId(userId)).thenReturn(Collections.emptyList());
        when(defaultStrategy.findPublicNotes(userId)).thenReturn(notes);

        List<Note> found =  recommendationService.findPublicNotes(userId);

        verify(defaultStrategy, times(1)).findPublicNotes(userId);
        assertEquals(notes, found);
    }

    @Test
    void findPublicNotes_habitBasedStrategy() {
        when(habitRepository.findByCreatorId(userId)).thenReturn(List.of(habit));
        when(habitNameBased.findPublicNotes(userId)).thenReturn(notes);

        List<Note> found =  recommendationService.findPublicNotes(userId);

        verify(habitNameBased, times(1)).findPublicNotes(userId);
        assertEquals(notes, found);
    }
}