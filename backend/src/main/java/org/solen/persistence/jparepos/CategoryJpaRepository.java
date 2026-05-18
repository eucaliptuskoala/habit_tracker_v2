package org.example.habit_tracker.persistence.jparepos;

import org.example.habit_tracker.persistence.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, Long> {
    List<CategoryEntity> findByParentIsNull();
    boolean existsByName(String name);
}
