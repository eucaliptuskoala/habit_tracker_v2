package org.example.habit_tracker.persistence.repositories;

import org.example.habit_tracker.domain.habits.Category;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.users.User;
import org.example.habit_tracker.persistence.converters.CategoryConverter;
import org.example.habit_tracker.persistence.converters.HabitConverter;
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
@Import({HabitRepository.class, HabitConverter.class, UserConverter.class, CategoryConverter.class})
class HabitRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private CategoryConverter categoryConverter;

    @Autowired
    private HabitJpaRepository jpaRepository;

    @Autowired
    private HabitRepository habitRepository;

    User user;
    Category category;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(null)
                .name("Test User")
                .email("email@email.com")
                .password("password")
                .isAdmin(false)
                .build();

        category = Category.builder()
                .id(null)
                .name("Test Category")
                .build();
    }

    private User persistUser() {
        var entity = entityManager.persist(userConverter.convertToEntity(user));
        entityManager.flush();
        return userConverter.convertToDomain(entity);
    }

    private Category persistCategory() {
        var entity = entityManager.persist(categoryConverter.convertToEntity(category));
        entityManager.flush();
        return categoryConverter.convertToDomain(entity);
    }

    private Habit newHabit(User creator, Category category) {
        return Habit.builder()
                .name("Drink Water")
                .description("8 glasses per day")
                .streak(0)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(3)
                .creator(creator)
                .category(category)
                .build();
    }

    @Test
    void saveHabitTest() {
        User creator = persistUser();
        Category cat = persistCategory();

        Habit saved = habitRepository.save(newHabit(creator, cat));

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Drink Water");
    }

    @Test
    void findHabitByIdTest() {
        User creator = persistUser();
        Category cat = persistCategory();

        Habit saved = habitRepository.save(newHabit(creator, cat));

        Habit found = habitRepository.findById(saved.getId());

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Drink Water");
    }

    @Test
    void findAllHabitsTest() {
        User creator = persistUser();
        Category cat = persistCategory();

        List<Habit> habits = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            habits.add(newHabit(creator, cat));
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
        Category cat = persistCategory();

        List<Habit> habits = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            habits.add(newHabit(creator, cat));
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
        Category cat = persistCategory();

        List<Habit> habits = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            habits.add(newHabit(creator, cat));
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
        Category cat = persistCategory();

        Habit saved = habitRepository.save(newHabit(creator, cat));

        habitRepository.deleteById(saved.getId());

        assertThat(habitRepository.findById(saved.getId())).isNull();
        assertThat(jpaRepository.findById(saved.getId())).isEmpty();
    }

    @Test
    void existsByHabitIdAndCreatorEmail(){
        User creator = persistUser();
        Category cat = persistCategory();

        Habit saved = habitRepository.save(newHabit(creator, cat));
        boolean exists = habitRepository.existsByHabitIdAndCreatorEmail(saved.getId(), creator.getEmail());

        assertThat(exists).isTrue();
    }
}