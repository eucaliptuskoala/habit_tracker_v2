package org.solen.business.checkin.fypstrategy;

import lombok.AllArgsConstructor;
import org.solen.business.repos.ICheckInRepository;
import org.solen.business.repos.IHabitRepository;
import org.solen.domain.checkin.CheckIn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

// Personalised FYP strategy: recommends public check-ins from habits that share
// at least one category with the user's own habits. Excludes the user's own entries.
//
// Flow:
//   1. Collect all category IDs from the user's habits
//   2. Fetch all public check-ins globally
//   3. Keep only those whose habit's category matches the user's categories
//   4. Exclude check-ins the user created themselves
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
