package org.example.habit_tracker.persistence.jparepos;

import org.example.habit_tracker.persistence.entities.HabitTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitTemplateJpaRepository extends JpaRepository<HabitTemplateEntity, Long> {
    boolean existsByName(String name);
}
