package org.example.habit_tracker.business.habitcases.creationstrategy;

import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
import org.example.habit_tracker.domain.habits.Habit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class HabitCreationStrategyService {

    @Qualifier("categoryCreationStrategy")
    private final IHabitCreationStrategy categoryStrategy;

    @Qualifier("customCreationStrategy")
    private final IHabitCreationStrategy custom;

    public HabitCreationStrategyService(
            @Qualifier("categoryCreationStrategy") IHabitCreationStrategy categoryStrategy,
            @Qualifier("customCreationStrategy") IHabitCreationStrategy custom
    ) {
        this.categoryStrategy = categoryStrategy;
        this.custom = custom;
    }

    public Habit getStrategy (CreateHabitRequest request, Long userId){
        if (request == null){
            throw new IllegalArgumentException("Request is null");
        }
        else if(request.getCategoryId() == null){
            return custom.createHabit(request, userId);
        }
        else{
            return categoryStrategy.createHabit(request, userId);
        }
    }
}