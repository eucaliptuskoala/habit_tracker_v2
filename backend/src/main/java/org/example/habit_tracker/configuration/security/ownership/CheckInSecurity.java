package org.example.habit_tracker.configuration.security.ownership;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.ICheckInRepository;
import org.springframework.stereotype.Component;

@Component("checkInSecurity")
@AllArgsConstructor
public class CheckInSecurity {

    private ICheckInRepository checkInRepository;

    public boolean isOwnerByEmail(Long checkInId, String email) {
        return checkInRepository.findPublicCheckIns().stream()
                .anyMatch(ci -> ci.getId().equals(checkInId)
                        && ci.getHabit().getCreator().getEmail().equals(email));
    }
}
