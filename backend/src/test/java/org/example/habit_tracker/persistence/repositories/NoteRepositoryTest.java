package org.example.habit_tracker.persistence.repositories;

import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.notes.Note;
import org.example.habit_tracker.domain.users.User;
import org.example.habit_tracker.persistence.converters.HabitConverter;
import org.example.habit_tracker.persistence.converters.HabitTemplateConverter;
import org.example.habit_tracker.persistence.converters.NoteConverter;
import org.example.habit_tracker.persistence.converters.UserConverter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({NoteRepository.class, NoteConverter.class, UserConverter.class, HabitConverter.class, HabitTemplateConverter.class})
class NoteRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private HabitConverter habitConverter;

    private User persistUser(User user) {
        var entity = entityManager.persist(userConverter.convertToEntity(user));
        entityManager.flush();
        return userConverter.convertToDomain(entity);
    }

    private Habit persistHabit(Habit habit) {
        var entity = entityManager.persist(habitConverter.convertToEntity(habit));
        entityManager.flush();
        return habitConverter.convertToDomain(entity);
    }

    private Note newNote(User creator, Habit habit) {
        return Note.builder()
                .title("Note Title")
                .content("Note Content")
                .personalFeeling(0)
                .isPublic(true)
                .creator(creator)
                .habit(habit)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void saveNote() {
        User user = persistUser(
                User.builder()
                        .name("user")
                        .email("user")
                        .password("password")
                        .isAdmin(false)
                        .build()
        );

        Habit habit = persistHabit(
                Habit.builder()
                        .name("habit")
                        .description("habit")
                        .streak(0)
                        .thresholdDays(2)
                        .creator(user)
                        .build()
        );

        Note saved = noteRepository.save(newNote(user, habit));
        assertNotNull(saved.getId());
    }


    @Test
    void findNoteByIdTest() {
        User user = persistUser(
                User.builder()
                        .name("user")
                        .email("user")
                        .password("password")
                        .isAdmin(false)
                        .build()
        );

        Habit habit = persistHabit(
                Habit.builder()
                        .name("habit")
                        .description("habit")
                        .streak(0)
                        .thresholdDays(2)
                        .creator(user)
                        .build()
        );

        Note saved = noteRepository.save(newNote(user, habit));
        Note found = noteRepository.findById(saved.getId());

        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Note Title");
    }


    @Test
    void findHabitsByCreatorIdTest(){
        User user = persistUser(
                User.builder()
                        .name("user")
                        .email("user")
                        .password("password")
                        .isAdmin(false)
                        .build()
        );

        Habit habit = persistHabit(
                Habit.builder()
                        .name("habit")
                        .description("habit")
                        .streak(0)
                        .thresholdDays(2)
                        .creator(user)
                        .build()
        );

        List<Note> notes = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            notes.add(newNote(user, habit));
        }

        for(Note note : notes){
            noteRepository.save(note);
        }

        List<Note> foundNotes =  noteRepository.findByCreatorId(user.getId());

        assertThat(foundNotes).hasSameSizeAs(notes);
    }

    @Test
    void existsByNoteIdAndCreatorEmail(){
        User user = persistUser(
                User.builder()
                        .name("user")
                        .email("user")
                        .password("password")
                        .isAdmin(false)
                        .build()
        );

        Habit habit = persistHabit(
                Habit.builder()
                        .name("habit")
                        .description("habit")
                        .streak(0)
                        .thresholdDays(2)
                        .creator(user)
                        .build()
        );

        Note saved = noteRepository.save(newNote(user, habit));
        boolean exists = noteRepository.existsByNoteIdAndCreatorEmail(saved.getId(), user.getEmail());

        assertThat(exists).isTrue();
    }

    @Test
    void deleteNote_success(){
        User user = persistUser(
                User.builder()
                        .name("user")
                        .email("user")
                        .password("password")
                        .isAdmin(false)
                        .build()
        );

        Habit habit = persistHabit(
                Habit.builder()
                        .name("habit")
                        .description("habit")
                        .streak(0)
                        .thresholdDays(2)
                        .creator(user)
                        .build()
        );

        Note saved = noteRepository.save(newNote(user, habit));

        noteRepository.deleteById(saved.getId());

        assertThat(noteRepository.findById(saved.getId())).isNull();
    }

    @Test
    void findPublicNotes(){
        User user = persistUser(
                User.builder()
                        .name("user")
                        .email("user")
                        .password("password")
                        .isAdmin(false)
                        .build()
        );

        Habit habit = persistHabit(
                Habit.builder()
                        .name("habit")
                        .description("habit")
                        .streak(0)
                        .thresholdDays(2)
                        .creator(user)
                        .build()
        );

        List<Note> notes = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            notes.add(newNote(user, habit));
        }

        for(Note note : notes){
            noteRepository.save(note);
        }

        List<Note> foundNotes =  noteRepository.findPublicNotes();

        assertThat(foundNotes).hasSameSizeAs(notes);
    }
}