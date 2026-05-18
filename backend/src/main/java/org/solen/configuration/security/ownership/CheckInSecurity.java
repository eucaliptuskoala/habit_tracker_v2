package org.solen.configuration.security.ownership;

import lombok.AllArgsConstructor;
import org.solen.business.repos.ICheckInRepository;
import org.springframework.stereotype.Component;

@Component("checkInSecurity")
@AllArgsConstructor
public class CheckInSecurity {

    private ICheckInRepository checkInRepository;

    public boolean isOwnerByEmail(Long checkInId, String email) {
        return checkInRepository.findByCheckInIdAndEmail(checkInId, email);
    }
}
