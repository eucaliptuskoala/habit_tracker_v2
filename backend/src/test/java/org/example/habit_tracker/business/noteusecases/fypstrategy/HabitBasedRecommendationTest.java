package org.example.habit_tracker.business.noteusecases.fypstrategy;

import org.example.habit_tracker.business.repos.IHabitRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HabitBasedRecommendationTest {

    @Mock
    private INoteRepository noteRepository;

    @Mock
    private IHabitRepository habitRepository;

    @InjectMocks
    private HabitBasedRecommendation habitBasedRecommendation;

    Long userId;
    User user;
    User other;
    Habit habit;
    List<Note> notes;

    @BeforeEach
    void setUp() {
        userId = 1L;

        user = User.builder()
                .id(1L)
                .name("User")
                .build();

        other = User.builder()
                .id(2L)
                .name("Other")
                .build();

        habit = Habit.builder()
                .id(1L)
                .name("Running")
                .creator(user)
                .build();

        notes = List.of(
                Note.builder().id(1L).creator(user).habit(habit).build(),
                Note.builder().id(2L).creator(user).habit(habit).build(),

                Note.builder().id(3L).creator(other).habit(habit).build()
        );
    }

    @Test
    void excludesUsersOwnNotes_andMatchesHabit() {
        when(habitRepository.findByCreatorId(userId)).thenReturn(List.of(habit));
        when(noteRepository.findPublicNotes()).thenReturn(notes);

        List<Note> result = habitBasedRecommendation.findPublicNotes(userId);

        assertEquals(1, result.size());

        Note recommended = result.get(0);
        assertEquals(3L, recommended.getId());

        assertNotEquals(userId, recommended.getCreator().getId());
        assertEquals("Running", recommended.getHabit().getName());
    }
}