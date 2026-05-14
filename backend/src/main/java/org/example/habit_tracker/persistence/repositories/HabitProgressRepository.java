package org.example.habit_tracker.persistence.repositories;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IHabitProgressRepository;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.example.habit_tracker.persistence.converters.HabitProgressConverter;
import org.example.habit_tracker.persistence.entities.HabitProgressEntity;
import org.example.habit_tracker.persistence.jparepos.HabitProgressJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@AllArgsConstructor
public class HabitProgressRepository implements IHabitProgressRepository {

    private HabitProgressConverter converter;
    private HabitProgressJpaRepository jpaRepository;

    @Override
    public HabitProgress save(HabitProgress progress) {
        HabitProgressEntity entity = jpaRepository.save(converter.convertToEntity(progress));
        return converter.convertToDomain(entity);
    }

    @Override
    public List<HabitProgress> findByHabitCreatorId(Long userId){
        return jpaRepository.findByHabitCreatorId(userId).stream().map(converter::convertToDomain).toList();
    }

    @Override
    public List<HabitProgress> findProgressForUser(Long userId, LocalDate from, LocalDate to) {
        return jpaRepository.findProgressForUser(userId, from, to).stream().map(converter::convertToDomain).toList();
    }
}