package org.example.habit_tracker.business.checkin;

import org.example.habit_tracker.domain.checkin.CheckIn;
import org.example.habit_tracker.domain.habits.Habit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckInTimelineBuilderTest {

    private final CheckInTimelineBuilder builder = new CheckInTimelineBuilder();

    @Test
    void buildTimeline_singleCheckIn() {
        Habit habit = Habit.builder()
                .id(1L)
                .name("Test")
                .streak(1)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(1)
                .build();

        CheckIn checkIn = CheckIn.builder()
                .id(1L)
                .habit(habit)
                .date(LocalDate.of(2026, 1, 1))
                .streakValue(1)
                .content("Test")
                .isPublic(true)
                .createdAt(LocalDateTime.now())
                .build();

        List<CheckIn> result = builder.buildTimeline(List.of(checkIn));

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getStreakValue());
    }

    @Test
    void buildTimeline_noStreakReset() {
        Habit habit = Habit.builder()
                .id(1L)
                .name("Test")
                .streak(2)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(3)
                .build();

        CheckIn ci1 = CheckIn.builder().id(1L).habit(habit).date(LocalDate.of(2026, 1, 1)).streakValue(1).build();
        CheckIn ci2 = CheckIn.builder().id(2L).habit(habit).date(LocalDate.of(2026, 1, 3)).streakValue(2).build();

        List<CheckIn> result = builder.buildTimeline(List.of(ci1, ci2));

        assertEquals(2, result.size());
    }
}
