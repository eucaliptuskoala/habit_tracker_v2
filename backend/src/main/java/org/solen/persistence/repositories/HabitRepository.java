package org.solen.persistence.repositories;

import lombok.AllArgsConstructor;
import org.solen.business.repos.IHabitRepository;
import org.solen.domain.habits.Habit;
import org.solen.persistence.converters.HabitConverter;
import org.solen.persistence.entities.HabitEntity;
import org.solen.persistence.jparepos.HabitJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class HabitRepository implements IHabitRepository {

    private HabitJpaRepository jpaRepository;
    private HabitConverter converter;

    @Override
    public Habit save(Habit habit) {
        HabitEntity entity = jpaRepository.save(converter.convertToEntity(habit));
        return converter.convertToDomain(entity);
    }

    @Override
    public Habit findById(Long id) {
        return jpaRepository.findById(id)
                .map(converter::convertToDomain)
                .orElse(null);
    }

    @Override
    public List<Habit> findAll() {
        return jpaRepository.findAll().stream().map(converter::convertToDomain).toList();
    }

    @Override
    public List<Habit> findByName(String name) {
        return jpaRepository.findByName(name).stream().map(converter::convertToDomain).toList();
    }

    @Override
    public List<Habit> findByCreatorId(Long userId) {
        return jpaRepository.findByCreator_Id(userId).stream().map(converter::convertToDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByHabitIdAndCreatorEmail(Long habitId, String email) {
        return jpaRepository.existsByIdAndCreatorEmail(habitId, email);
    }
}