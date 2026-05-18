package org.example.habit_tracker.business.checkin;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.CheckInNotFoundException;
import org.example.habit_tracker.business.repos.ICheckInRepository;
import org.example.habit_tracker.domain.checkin.CheckIn;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteCheckInUseCaseImpl implements IDeleteCheckInUseCase {

    private ICheckInRepository checkInRepository;

    @Override
    public void delete(Long id) {
        CheckIn existing = checkInRepository.findById(id);
        if (existing == null) {
            throw new CheckInNotFoundException(id);
        }
        checkInRepository.deleteById(id);
    }
}
