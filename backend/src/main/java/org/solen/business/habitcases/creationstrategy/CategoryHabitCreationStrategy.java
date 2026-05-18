package org.solen.business.habitcases.creationstrategy;

import lombok.AllArgsConstructor;
import org.solen.business.exceptions.CategoryNotFoundByIdException;
import org.solen.business.exceptions.HabitAlreadyExistsException;
import org.solen.business.exceptions.UserNotFoundByIdException;
import org.solen.business.repos.ICategoryRepository;
import org.solen.business.repos.IHabitRepository;
import org.solen.business.repos.IUserRepository;
import org.solen.controller.dto.habit.CreateHabitRequest;
import org.solen.domain.habits.Category;
import org.solen.domain.habits.Habit;
import org.solen.domain.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryCreationStrategy")
@AllArgsConstructor
public class CategoryHabitCreationStrategy implements IHabitCreationStrategy {

    private ICategoryRepository categoryRepository;
    private IHabitRepository habitRepository;
    private IUserRepository userRepository;

    @Override
    public Habit createHabit(CreateHabitRequest request, Long userId) {
        Category category = categoryRepository.findById(request.getCategoryId());

        if(category == null) {
            throw new CategoryNotFoundByIdException(request.getCategoryId());
        }

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
                .category(category)
                .build();

        return habitRepository.save(habit);
    }

    public static String normalizeName(String name) {
        name = name.trim().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
