package org.example.habit_tracker.persistence.repositories;

import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.example.habit_tracker.persistence.converters.HabitTemplateConverter;
import org.example.habit_tracker.persistence.entities.HabitTemplateEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({HabitTemplateRepository.class, HabitTemplateConverter.class})
class HabitTemplateRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HabitTemplateConverter converter;

    @Autowired
    private HabitTemplateRepository repository;

    HabitTemplate habitTemplate;

    @BeforeEach
    void setup(){
        habitTemplate = HabitTemplate.builder()
                .name("Habit Template")
                .popularity(1)
                .build();
    }

    private HabitTemplate persistHabitTemplate(HabitTemplate template) {
        HabitTemplateEntity entity = entityManager.persist(converter.convertToEntity(template));
        entityManager.flush();
        return converter.convertToDomain(entity);
    }

    @Test
    void saveTemplate_success() {
        HabitTemplate saved = repository.save(habitTemplate);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void findById_success() {
        HabitTemplate persisted = persistHabitTemplate(habitTemplate);

        HabitTemplate found = repository.findById(persisted.getId());

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(persisted.getId());
    }

    @Test
    void findByName_success() {
        HabitTemplate persisted = persistHabitTemplate(habitTemplate);
        boolean isFound = repository.existsByName(persisted.getName());
        assertThat(isFound).isTrue();
    }

    @Test
    void findByName_fail() {
        persistHabitTemplate(habitTemplate);
        boolean isFound = repository.existsByName("Another Habit Template");

        assertThat(isFound).isFalse();
    }

    @Test
    void findAll_success() {
        for(int i = 0; i < 3; i++){
            persistHabitTemplate(habitTemplate);
        }

        List<HabitTemplate> all = repository.findAll();

        assertThat(all).isNotNull().hasSize(3);
    }

    @Test
    void deleteById_success() {
        HabitTemplate persisted = persistHabitTemplate(habitTemplate);

        repository.deleteById(persisted.getId());
        entityManager.flush();

        assertThat(repository.findById(persisted.getId())).isNull();
    }
}