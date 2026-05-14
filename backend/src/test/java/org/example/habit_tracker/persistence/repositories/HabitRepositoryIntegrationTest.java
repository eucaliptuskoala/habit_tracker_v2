package org.example.habit_tracker.persistence.repositories;

import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.example.habit_tracker.domain.users.User;
import org.example.habit_tracker.persistence.converters.HabitConverter;
import org.example.habit_tracker.persistence.converters.HabitTemplateConverter;
import org.example.habit_tracker.persistence.converters.UserConverter;
import org.example.habit_tracker.persistence.jparepos.HabitJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({HabitRepository.class, HabitConverter.class, UserConverter.class, HabitTemplateConverter.class})
class HabitRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private HabitTemplateConverter habitTemplateConverter;

    @Autowired
    private HabitJpaRepository jpaRepository;

    @Autowired
    private HabitRepository habitRepository;

    User user;
    HabitTemplate template;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(null)
                .name("Test User")
                .email("email@email.com")
                .password("password")
                .isAdmin(false)
                .build();

        template = HabitTemplate.builder()
                .id(null)
                .name("Test1")
                .popularity(1)
                .build();
    }

    private User persistUser() {
        var entity = entityManager.persist(userConverter.convertToEntity(user));
        entityManager.flush();
        return userConverter.convertToDomain(entity);
    }

    private HabitTemplate persistTemplate() {
        var entity = entityManager.persist(habitTemplateConverter.convertToEntity(template));
        entityManager.flush();
        return habitTemplateConverter.convertToDomain(entity);
    }

    private Habit newHabit(User creator, HabitTemplate template) {
        return Habit.builder()
                .name("Drink Water")
                .description("8 glasses per day")
                .streak(0)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(3)
                .creator(creator)
                .template(template)
                .build();
    }

    @Test
    void saveHabitTest() {
        User creator = persistUser();
        HabitTemplate habitTemplate = persistTemplate();

        Habit saved = habitRepository.save(newHabit(creator, habitTemplate));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Drink Water");
    }

    @Test
    void findHabitByIdTest() {
        User creator = persistUser();
        HabitTemplate habitTemplate = persistTemplate();

        Habit saved = habitRepository.save(newHabit(creator, habitTemplate));

        Habit found = habitRepository.findById(saved.getId());

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Drink Water");
    }

    @Test
    void findAllHabitsTest() {
        User creator = persistUser();
        HabitTemplate habitTemplate = persistTemplate();

        List<Habit> habits = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            habits.add(newHabit(creator, habitTemplate));
        }

        for(Habit habit : habits){
            habitRepository.save(habit);
        }

        List<Habit> foundHabits =  habitRepository.findAll();

        assertThat(foundHabits).hasSameSizeAs(habits);
    }

    @Test
    void findHabitsByNameTest() {
        User creator = persistUser();
        HabitTemplate habitTemplate = persistTemplate();

        List<Habit> habits = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            habits.add(newHabit(creator, habitTemplate));
        }

        for(Habit habit : habits){
            habitRepository.save(habit);
        }

        List<Habit> foundHabits =  habitRepository.findByName("Drink Water");

        assertThat(foundHabits).hasSameSizeAs(habits);
    }

    @Test
    void findHabitsByCreatorIdTest(){
        User creator = persistUser();
        HabitTemplate habitTemplate = persistTemplate();

        List<Habit> habits = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            habits.add(newHabit(creator, habitTemplate));
        }

        for(Habit habit : habits){
            habitRepository.save(habit);
        }

        List<Habit> foundHabits =  habitRepository.findByCreatorId(creator.getId());

        assertThat(foundHabits).hasSameSizeAs(habits);
    }

    @Test
    void deleteHabitTest() {
        User creator = persistUser();
        HabitTemplate habitTemplate = persistTemplate();

        Habit saved = habitRepository.save(newHabit(creator, habitTemplate));

        habitRepository.deleteById(saved.getId());

        assertThat(habitRepository.findById(saved.getId())).isNull();
        assertThat(jpaRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void existsByHabitIdAndCreatorEmail(){
        User creator = persistUser();
        HabitTemplate habitTemplate = persistTemplate();

        Habit saved = habitRepository.save(newHabit(creator, habitTemplate));
        boolean exists = habitRepository.existsByHabitIdAndCreatorEmail(saved.getId(), creator.getEmail());

        assertThat(exists).isTrue();
    }
}