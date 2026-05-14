package org.example.habit_tracker.persistence.repositories;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.INoteRepository;
import org.example.habit_tracker.domain.notes.Note;
import org.example.habit_tracker.persistence.converters.NoteConverter;
import org.example.habit_tracker.persistence.entities.NoteEntity;
import org.example.habit_tracker.persistence.jparepos.NoteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class NoteRepository implements INoteRepository {

    private NoteJpaRepository jpaRepository;
    private NoteConverter converter;

    @Override
    public Note save(Note note) {
        NoteEntity entity = jpaRepository.save(converter.convertToEntity(note));
        return converter.convertToDomain(entity);
    }

    @Override
    public boolean existsByNoteIdAndCreatorEmail(Long id, String email){
        return jpaRepository.existsByIdAndCreatorEmail(id, email);
    }

    @Override
    public List<Note> findByCreatorId(Long userId){
        List<NoteEntity> noteEntities = jpaRepository.findByCreatorId(userId);
        return noteEntities.stream().map(converter::convertToDomain).toList();
    }

    @Override
    public Note findById(Long id){
        return jpaRepository.findById(id)
                .map(converter::convertToDomain)
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Note> findPublicNotes(){
        List<NoteEntity> noteEntities = jpaRepository.findByIsPublicTrue();
        return noteEntities.stream().map(converter::convertToDomain).toList();
    }
}