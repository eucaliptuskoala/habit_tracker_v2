package org.example.habit_tracker.persistence.jparepos;

import org.example.habit_tracker.persistence.entities.HabitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitJpaRepository extends JpaRepository<HabitEntity, Long> {
    List<HabitEntity> findByName(String name);
    List<HabitEntity> findByCreator_Id(Long userId);
    boolean existsByIdAndCreatorEmail(Long id, String email);
}
