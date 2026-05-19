package org.solen.business.habitcases.creationstrategy;

import lombok.AllArgsConstructor;
import org.solen.business.exceptions.HabitAlreadyExistsException;
import org.solen.business.exceptions.UserNotFoundByIdException;
import org.solen.business.repos.IHabitRepository;
import org.solen.business.repos.IUserRepository;
import org.solen.controller.dto.habit.CreateHabitRequest;
import org.solen.domain.habits.Habit;
import org.solen.domain.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

// Strategy: creates a free-form habit without linking it to a category.
// Used when the client does NOT provide a categoryId in the create request.
// Identical logic to CategoryHabitCreationStrategy but with category set to null.
@Service("customCreationStrategy")
@AllArgsConstructor
public class CustomHabitCreationStrategy implements IHabitCreationStrategy {

    private IHabitRepository habitRepository;
    private IUserRepository userRepository;

    @Override
    public Habit createHabit(CreateHabitRequest request, Long userId) {

        User user = userRepository.findById(userId);

        if(user == null) {
            throw new UserNotFoundByIdException(userId);
        }

        List<Habit> existingHabits = habitRepository.findByCreatorId(user.getId());

        String newHabitName = normalizeName(request.getName());

        if(existingHabits.stream().anyMatch(habit -> habit.getName().equals(newHabitName))) {
            throw new HabitAlreadyExistsException();
        }

        Habit habit = Habit.builder()
                .name(normalizeName(request.getName()))
                .description(request.getDescription())
                .streak(0)
                .thresholdDays(1)
                .creator(user)
                .category(null)
                .build();

        Habit savedHabit = habitRepository.save(habit);
        return savedHabit;
    }

    public static String normalizeName(String name) {
        name = name.trim().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}