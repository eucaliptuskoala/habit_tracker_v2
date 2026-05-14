package org.example.habit_tracker.persistence.repositories;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IHabitTemplateRepository;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.example.habit_tracker.persistence.converters.HabitTemplateConverter;
import org.example.habit_tracker.persistence.entities.HabitTemplateEntity;
import org.example.habit_tracker.persistence.jparepos.HabitTemplateJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class HabitTemplateRepository implements IHabitTemplateRepository {

    private HabitTemplateJpaRepository jpaRepository;
    private HabitTemplateConverter converter;

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public HabitTemplate save(HabitTemplate template) {
        HabitTemplateEntity entity = jpaRepository.save(converter.convertToEntity(template));
        return converter.convertToDomain(entity);
    }

    @Override
    public HabitTemplate findById(Long id) {
        HabitTemplateEntity entity = jpaRepository.findById(id).orElse(null);
        return converter.convertToDomain(entity);
    }

    @Override
    public List<HabitTemplate> findAll() {
        return jpaRepository.findAll().stream().map(converter::convertToDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}