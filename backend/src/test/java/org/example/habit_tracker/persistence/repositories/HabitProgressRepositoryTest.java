package org.example.habit_tracker.persistence.repositories;

import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.example.habit_tracker.domain.users.User;
import org.example.habit_tracker.persistence.converters.HabitConverter;
import org.example.habit_tracker.persistence.converters.HabitProgressConverter;
import org.example.habit_tracker.persistence.converters.HabitTemplateConverter;
import org.example.habit_tracker.persistence.converters.UserConverter;
import org.example.habit_tracker.persistence.jparepos.HabitProgressJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({
        HabitProgressRepository.class,
        HabitProgressConverter.class,
        HabitConverter.class,
        UserConverter.class,
        HabitTemplateConverter.class,
        HabitRepository.class
})
class HabitProgressRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HabitProgressRepository habitProgressRepository;

    @Autowired
    private HabitProgressJpaRepository jpaRepository;

    @Autowired
    private HabitProgressConverter progressConverter;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private HabitConverter habitConverter;

    @Autowired
    private HabitTemplateConverter templateConverter;

    private User user;
    private HabitTemplate template;
    private Habit habit;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("Test User")
                .email("email@email.com")
                .password("password")
                .isAdmin(false)
                .build();

        template = HabitTemplate.builder()
                .name("Test Template")
                .popularity(1)
                .build();

        var persistedUser = entityManager.persist(userConverter.convertToEntity(user));
        var persistedTemplate = entityManager.persist(templateConverter.convertToEntity(template));
        entityManager.flush();

        user.setId(persistedUser.getId());
        template.setId(persistedTemplate.getId());

        habit = Habit.builder()
                .name("Drink Water")
                .description("8 glasses per day")
                .streak(0)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(3)
                .creator(user)
                .template(template)
                .build();

        var persistedHabit = entityManager.persist(habitConverter.convertToEntity(habit));
        entityManager.flush();
        habit.setId(persistedHabit.getId());
    }

    private HabitProgress newProgress(Habit habit, LocalDate date, int streakValue) {
        return HabitProgress.builder()
                .habit(habit)
                .date(date)
                .streakValue(streakValue)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void saveProgressTest() {
        HabitProgress progress = habitProgressRepository.save(newProgress(habit, LocalDate.now(), 1));

        assertThat(progress.getId()).isNotNull();
        assertThat(progress.getHabit().getId()).isEqualTo(habit.getId());
        assertThat(progress.getStreakValue()).isEqualTo(1);
    }

    @Test
    void findByHabitCreatorIdTest() {
        HabitProgress saved = habitProgressRepository.save(newProgress(habit, LocalDate.now(), 1));

        List<HabitProgress> found = habitProgressRepository.findByHabitCreatorId(user.getId());

        assertThat(found).isNotNull();
        assertThat(found.get(0).getHabit().getId()).isEqualTo(habit.getId());
    }

    @Test
    void findProgressForUserTest() {
        HabitProgress progress1 = habitProgressRepository.save(newProgress(habit, LocalDate.now().minusDays(1), 1));
        HabitProgress progress2 = habitProgressRepository.save(newProgress(habit, LocalDate.now(), 2));

        List<HabitProgress> found = habitProgressRepository.findProgressForUser(
                user.getId(),
                LocalDate.now().minusDays(1),
                LocalDate.now()
        );

        assertThat(found).isNotNull();
        assertThat(found.get(0).getStreakValue()).isEqualTo(1);
        assertThat(found.get(1).getStreakValue()).isEqualTo(2);
    }

}