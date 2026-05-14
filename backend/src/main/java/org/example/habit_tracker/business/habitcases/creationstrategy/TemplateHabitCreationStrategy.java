package org.example.habit_tracker.business.habitcases.creationstrategy;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.HabitAlreadyExistsException;
import org.example.habit_tracker.business.exceptions.TemplateNotFoundException;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.habittemplatecases.IUpdatePopularityUseCase;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.business.repos.IHabitTemplateRepository;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.example.habit_tracker.domain.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("templateCreationStrategy")
@AllArgsConstructor
public class TemplateHabitCreationStrategy implements IHabitCreationStrategy {

    private IHabitTemplateRepository templateRepository;
    private IHabitRepository habitRepository;
    private IUserRepository userRepository;

    private IUpdatePopularityUseCase updatePopularityUseCase;

    @Override
    public Habit createHabit(CreateHabitRequest request, Long userId) {
        HabitTemplate template = templateRepository.findById(request.getHabitTemplateId());

        if(template == null) {
            throw new TemplateNotFoundException(request.getHabitTemplateId());
        }

        User user = userRepository.findById(userId);
        if(user == null) {
            throw new UserNotFoundByIdException(userId);
        }

        List<Habit> existingHabits = habitRepository.findByCreatorId(user.getId());
        String newHabitName = template.getName();

        if(existingHabits.stream().anyMatch(habit -> habit.getName().equals(newHabitName))) {
            throw new HabitAlreadyExistsException();
        }

        Habit habit = Habit.builder()
                .name(template.getName())
                .description(request.getDescription())
                .streak(0)
                .thresholdDays(1)
                .creator(user)
                .template(template)
                .build();

        Habit savedHabit = habitRepository.save(habit);
        updatePopularityUseCase.updatePopularity(request.getHabitTemplateId());

        return savedHabit;
    }
}