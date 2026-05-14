package org.example.habit_tracker.persistence.jparepos;

import org.example.habit_tracker.persistence.entities.HabitProgressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HabitProgressJpaRepository extends JpaRepository<HabitProgressEntity, Long> {
    @Query("""
        select hp
        from HabitProgressEntity hp
        join hp.habit h
        where h.creator.id = :userId
    """)
    List<HabitProgressEntity> findByHabitCreatorId(@Param("userId") Long userId);

    @Query("""
        select hp
        from HabitProgressEntity hp
        join hp.habit h
        where h.creator.id = :userId
          and hp.date between :from and :to
    """)
    List<HabitProgressEntity> findProgressForUser(
            @Param("userId") Long userId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
}
