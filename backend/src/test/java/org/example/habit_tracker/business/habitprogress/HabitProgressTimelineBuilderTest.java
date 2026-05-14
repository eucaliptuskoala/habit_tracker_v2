package org.example.habit_tracker.business.habitprogress;

import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class HabitProgressTimelineBuilderTest {

    private HabitProgressTimelineBuilder builder;
    private Habit habit;

    @BeforeEach
    void setUp() {
        builder = new HabitProgressTimelineBuilder();

        habit = Habit.builder()
                .id(1L)
                .name("Test Habit")
                .thresholdDays(2)
                .build();
    }

    @Test
    void buildTimelineSingleProgress() {
        HabitProgress progress = HabitProgress.builder()
                .habit(habit)
                .date(LocalDate.of(2026, 1, 1))
                .streakValue(1)
                .build();

        List<HabitProgress> result = builder.buildTimeline(List.of(progress), LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 3));

        assertThat(result).hasSize(3);
        assertThat(result.get(0).getStreakValue()).isEqualTo(1);
        assertThat(result.get(1).getStreakValue()).isEqualTo(1);
        assertThat(result.get(2).getStreakValue()).isEqualTo(1);
    }

    @Test
    void buildTimelineBreakStreak() {
        HabitProgress progress = HabitProgress.builder()
                .habit(habit)
                .date(LocalDate.of(2026, 1, 1))
                .streakValue(1)
                .build();

        List<HabitProgress> result = builder.buildTimeline(List.of(progress), LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 5));

        assertThat(result).hasSize(5);
        assertThat(result.get(3).getStreakValue()).isEqualTo(0); // threshold exceeded
        assertThat(result.get(4).getStreakValue()).isEqualTo(0);
    }

}