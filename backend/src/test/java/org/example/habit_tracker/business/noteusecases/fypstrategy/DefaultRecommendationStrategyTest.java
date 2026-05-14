package org.example.habit_tracker.business.noteusecases.fypstrategy;

import org.example.habit_tracker.business.repos.INoteRepository;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.notes.Note;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultRecommendationStrategyTest {
    @Mock
    private INoteRepository noteRepository;

    @InjectMocks
    private DefaultRecommendationStrategy defaultRecommendationStrategy;

    private Long userId;
    private List<Note> notes;

    @BeforeEach
    void setUp() {
        userId = 1L;

        User user = User.builder().id(1L).build();
        User other = User.builder().id(2L).build();

        Habit habit = Habit.builder().name("Running").build();

        notes = List.of(
                Note.builder().id(1L).creator(user).habit(habit).build(),
                Note.builder().id(2L).creator(other).habit(habit).build(),
                Note.builder().id(3L).creator(other).habit(habit).build()
        );
    }

    @Test
    void returnsAllPublicNotesWithoutFiltering() {
        when(noteRepository.findPublicNotes()).thenReturn(notes);

        List<Note> result = defaultRecommendationStrategy.findPublicNotes(userId);

        assertEquals(3, result.size());
        assertEquals(notes, result);
    }
}