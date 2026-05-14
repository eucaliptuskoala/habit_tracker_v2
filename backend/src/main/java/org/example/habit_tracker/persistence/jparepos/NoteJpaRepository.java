package org.example.habit_tracker.persistence.jparepos;

import org.example.habit_tracker.persistence.entities.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteJpaRepository extends JpaRepository<NoteEntity,Long> {
    boolean existsByIdAndCreatorEmail(Long id, String email);
    List<NoteEntity> findByCreatorId(Long userId);
    List<NoteEntity> findByIsPublicTrue();
}