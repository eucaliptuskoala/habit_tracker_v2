package org.example.habit_tracker.configuration;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.springframework.stereotype.Component;

@Component("habitSecurity")
@AllArgsConstructor
public class HabitSecurity {

    private IHabitRepository habitRepository;

    public boolean isOwnerByEmail(Long habitId, String email) {
        return habitRepository.existsByHabitIdAndCreatorEmail(habitId, email);
    }
}