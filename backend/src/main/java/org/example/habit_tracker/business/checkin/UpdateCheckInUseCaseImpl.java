package org.example.habit_tracker.business.checkin;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.CheckInNotFoundException;
import org.example.habit_tracker.business.repos.ICheckInRepository;
import org.example.habit_tracker.domain.checkin.CheckIn;
import org.example.habit_tracker.domain.checkin.Mood;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateCheckInUseCaseImpl implements IUpdateCheckInUseCase {

    private ICheckInRepository checkInRepository;

    @Override
    public CheckIn update(Long id, String content, boolean isPublic, Mood mood) {
        CheckIn existing = checkInRepository.findCheckInsForUser(id, null, null).stream()
                .filter(ci -> ci.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (existing == null) {
            throw new CheckInNotFoundException(id);
        }

        existing.setContent(content);
        existing.setPublic(isPublic);
        existing.setMood(mood);

        return checkInRepository.save(existing);
    }
}
