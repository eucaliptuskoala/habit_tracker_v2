package org.example.habit_tracker.business.checkin.fypstrategy;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.ICheckInRepository;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.domain.checkin.CheckIn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("habitNameBased")
@AllArgsConstructor
public class HabitBasedRecommendation implements IRecommendationStrategy {

    private ICheckInRepository checkInRepository;
    private IHabitRepository habitRepository;

    @Override
    public List<CheckIn> findPublicCheckIns(Long userId) {
        List<Long> userCategoryIds = habitRepository.findByCreatorId(userId).stream()
                .map(habit -> habit.getCategory() != null ? habit.getCategory().getId() : null)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        return checkInRepository.findPublicCheckIns().stream()
                .filter(ci -> !ci.getHabit().getCreator().getId().equals(userId))
                .filter(ci -> ci.getHabit().getCategory() != null
                        && userCategoryIds.contains(ci.getHabit().getCategory().getId()))
                .toList();
    }
}
