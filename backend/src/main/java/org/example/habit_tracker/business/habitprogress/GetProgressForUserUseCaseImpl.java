package org.example.habit_tracker.business.habitprogress;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IHabitProgressRepository;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class GetProgressForUserUseCaseImpl implements IGetProgressForUserUseCase {

    private IHabitProgressRepository habitProgressRepository;
    private HabitProgressTimelineBuilder timelineBuilder;

    @Override
    public List<HabitProgress> getProgressForUser(Long userId, LocalDate from, LocalDate to) {

        List<HabitProgress> rawProgress =
                (from != null && to != null)
                        ? habitProgressRepository.findProgressForUser(userId, from, to)
                        : habitProgressRepository.findByHabitCreatorId(userId);

        if (rawProgress.isEmpty()) {
            return List.of();
        }

        return timelineBuilder.buildTimeline(rawProgress, from, to);
    }
}
