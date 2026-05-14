package org.example.habit_tracker.business.habitprogress;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class HabitProgressTimelineBuilder {

    public List<HabitProgress> buildTimeline(List<HabitProgress> rawProgress, LocalDate from, LocalDate to) {

        Map<Long, List<HabitProgress>> byHabit = rawProgress.stream()
                .collect(Collectors.groupingBy(p -> p.getHabit().getId()));

        List<HabitProgress> result = new ArrayList<>();

        for (List<HabitProgress> habitProgresses : byHabit.values()) {

            habitProgresses.sort(Comparator.comparing(HabitProgress::getDate));

            Habit habit = habitProgresses.get(0).getHabit();
            int thresholdDays = habit.getThresholdDays();

            LocalDate start = from != null ? from : habitProgresses.get(0).getDate();
            LocalDate end = to != null ? to : LocalDate.now();

            Map<LocalDate, HabitProgress> byDate = habitProgresses.stream()
                    .collect(Collectors.toMap(HabitProgress::getDate, hp -> hp));

            int currentStreak = 0;
            LocalDate lastUpdateDate = null;

            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {

                HabitProgress stored = byDate.get(date);

                if (stored != null) {

                    currentStreak = stored.getStreakValue();
                    lastUpdateDate = date;

                    result.add(
                            HabitProgress.builder()
                                    .id(stored.getId())
                                    .habit(stored.getHabit())
                                    .date(date)
                                    .streakValue(currentStreak)
                                    .createdAt(stored.getCreatedAt())
                                    .build()
                    );

                } else {

                    if (lastUpdateDate != null &&
                            ChronoUnit.DAYS.between(lastUpdateDate, date) > thresholdDays) {
                        currentStreak = 0;
                    }

                    result.add(
                            HabitProgress.builder()
                                    .habit(habit)
                                    .date(date)
                                    .streakValue(currentStreak)
                                    .createdAt(LocalDateTime.now())
                                    .build()
                    );
                }
            }
        }

        return result.stream()
                .sorted(Comparator
                        .comparing((HabitProgress hp) -> hp.getHabit().getId())
                        .thenComparing(HabitProgress::getDate))
                .toList();
    }
}
