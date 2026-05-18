package org.example.habit_tracker.business.checkin;

import org.example.habit_tracker.domain.checkin.CheckIn;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CheckInTimelineBuilder {

    public List<CheckIn> buildTimeline(List<CheckIn> rawCheckIns) {
        Map<Long, List<CheckIn>> groupedByHabit = rawCheckIns.stream()
                .collect(Collectors.groupingBy(ci -> ci.getHabit().getId()));

        List<CheckIn> result = new ArrayList<>();

        for (Map.Entry<Long, List<CheckIn>> entry : groupedByHabit.entrySet()) {
            List<CheckIn> sorted = entry.getValue().stream()
                    .sorted(Comparator.comparing(CheckIn::getDate))
                    .toList();

            int threshold = sorted.get(0).getHabit().getThresholdDays();

            for (CheckIn ci : sorted) {
                if (!result.isEmpty()) {
                    CheckIn last = result.get(result.size() - 1);
                    if (last.getHabit().getId().equals(ci.getHabit().getId())) {
                        long daysBetween = ci.getDate().toEpochDay() - last.getDate().toEpochDay();
                        if (daysBetween > threshold) {
                            ci.setStreakValue(1);
                        }
                    }
                }
                result.add(ci);
            }
        }

        result.sort(Comparator.comparing(CheckIn::getDate).reversed());
        return result;
    }
}
