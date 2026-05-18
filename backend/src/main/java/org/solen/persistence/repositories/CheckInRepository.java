package org.example.habit_tracker.persistence.repositories;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.ICheckInRepository;
import org.example.habit_tracker.domain.checkin.CheckIn;
import org.example.habit_tracker.persistence.converters.CheckInConverter;
import org.example.habit_tracker.persistence.entities.CheckInEntity;
import org.example.habit_tracker.persistence.jparepos.CheckInJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@AllArgsConstructor
public class CheckInRepository implements ICheckInRepository {

    private CheckInConverter converter;
    private CheckInJpaRepository jpaRepository;

    @Override
    public CheckIn findById(Long id) {
        CheckInEntity entity = jpaRepository.findByIdWithHabitAndCreator(id);
        return entity != null ? converter.convertToDomain(entity) : null;
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean findByCheckInIdAndEmail(Long checkInId, String email) {
        return jpaRepository.findByCheckInIdAndEmail(checkInId, email);
    }

    @Override
    public CheckIn save(CheckIn checkIn) {
        CheckInEntity entity = jpaRepository.save(converter.convertToEntity(checkIn));
        return converter.convertToDomain(entity);
    }

    @Override
    public List<CheckIn> findByHabitCreatorId(Long userId){
        return jpaRepository.findByHabitCreatorId(userId).stream().map(converter::convertToDomain).toList();
    }

    @Override
    public List<CheckIn> findCheckInsForUser(Long userId, LocalDate from, LocalDate to) {
        return jpaRepository.findCheckInsForUser(userId, from, to).stream().map(converter::convertToDomain).toList();
    }

    @Override
    public List<CheckIn> findPublicCheckIns() {
        return jpaRepository.findPublicCheckIns().stream().map(converter::convertToDomain).toList();
    }
}
