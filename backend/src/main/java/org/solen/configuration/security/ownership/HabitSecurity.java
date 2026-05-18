package org.solen.configuration.security.ownership;

import lombok.AllArgsConstructor;
import org.solen.business.repos.IHabitRepository;
import org.springframework.stereotype.Component;

@Component("habitSecurity")
@AllArgsConstructor
public class HabitSecurity {

    private IHabitRepository habitRepository;

    public boolean isOwnerByEmail(Long habitId, String email) {
        return habitRepository.existsByHabitIdAndCreatorEmail(habitId, email);
    }
}
