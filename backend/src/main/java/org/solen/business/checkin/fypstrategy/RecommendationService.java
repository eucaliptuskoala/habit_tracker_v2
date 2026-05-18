package org.solen.business.checkin.fypstrategy;

import org.solen.business.repos.IHabitRepository;
import org.solen.domain.checkin.CheckIn;
import org.solen.domain.habits.Habit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    @Qualifier("habitNameBased")
    private final IRecommendationStrategy habitNameBased;

    @Qualifier("default")
    private final IRecommendationStrategy defaultStrategy;

    private final IHabitRepository habitRepository;

    public RecommendationService(
            @Qualifier("habitNameBased") IRecommendationStrategy habitNameBased,
            @Qualifier("default") IRecommendationStrategy defaultStrategy,
            IHabitRepository habitRepository
    ) {
        this.habitNameBased = habitNameBased;
        this.defaultStrategy = defaultStrategy;
        this.habitRepository = habitRepository;
    }

    public List<CheckIn> findPublicCheckIns(Long userId) {
        List<Habit> userHabits = habitRepository.findByCreatorId(userId);

        if (userHabits.isEmpty()) {
            return defaultStrategy.findPublicCheckIns(userId);
        } else {
            return habitNameBased.findPublicCheckIns(userId);
        }
    }
}
