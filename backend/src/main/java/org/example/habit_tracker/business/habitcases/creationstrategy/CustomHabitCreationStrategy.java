package org.example.habit_tracker.business.habitcases.creationstrategy;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.HabitAlreadyExistsException;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.habittemplatecases.ICreateHabitTemplateUseCase;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customCreationStrategy")
@AllArgsConstructor
public class CustomHabitCreationStrategy implements IHabitCreationStrategy {

    private IHabitRepository habitRepository;
    private IUserRepository userRepository;
    private ICreateHabitTemplateUseCase createHabitTemplateUseCase;

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
                .template(null)
                .build();

        Habit savedHabit = habitRepository.save(habit);
        createHabitTemplateUseCase.createHabitTemplate(savedHabit);
        return savedHabit;
    }

    public static String normalizeName(String name) {
        name = name.trim().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}