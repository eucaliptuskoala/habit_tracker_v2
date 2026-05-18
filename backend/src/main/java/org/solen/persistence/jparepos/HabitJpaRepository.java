package org.solen.persistence.jparepos;

import org.solen.persistence.entities.HabitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HabitJpaRepository extends JpaRepository<HabitEntity, Long> {
    List<HabitEntity> findByName(String name);
    List<HabitEntity> findByCreator_Id(Long userId);
    boolean existsByIdAndCreatorEmail(Long id, String email);
}
