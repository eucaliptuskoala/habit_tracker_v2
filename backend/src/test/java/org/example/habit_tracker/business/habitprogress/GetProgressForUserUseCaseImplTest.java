package org.example.habit_tracker.business.habitprogress;

import org.example.habit_tracker.business.repos.IHabitProgressRepository;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProgressForUserUseCaseImplTest {

    @Mock
    private IHabitProgressRepository progressRepository;

    @Mock
    private HabitProgressTimelineBuilder timelineBuilder;

    @InjectMocks
    private GetProgressForUserUseCaseImpl getProgressUseCase;

    private HabitProgress progress;

    @BeforeEach
    void setUp() {
        progress = HabitProgress.builder()
                .streakValue(1)
                .build();
    }

    @Test
    void getProgressWithDateRange() {
        when(progressRepository.findProgressForUser(1L, LocalDate.now(), LocalDate.now()))
                .thenReturn(List.of(progress));
        when(timelineBuilder.buildTimeline(anyList(), any(), any()))
                .thenReturn(List.of(progress));

        List<HabitProgress> result = getProgressUseCase.getProgressForUser(1L, LocalDate.now(), LocalDate.now());

        assertThat(result).hasSize(1);
        verify(progressRepository, times(1)).findProgressForUser(1L, LocalDate.now(), LocalDate.now());
        verify(timelineBuilder, times(1)).buildTimeline(anyList(), any(), any());
    }

    @Test
    void getProgressWithoutDateRange() {
        when(progressRepository.findByHabitCreatorId(1L)).thenReturn(List.of(progress));
        when(timelineBuilder.buildTimeline(anyList(), any(), any())).thenReturn(List.of(progress));

        List<HabitProgress> result = getProgressUseCase.getProgressForUser(1L, null, null);

        assertThat(result).hasSize(1);
        verify(progressRepository, times(1)).findByHabitCreatorId(1L);
        verify(timelineBuilder, times(1)).buildTimeline(anyList(), any(), any());
    }

    @Test
    void getProgressEmptyList() {
        when(progressRepository.findByHabitCreatorId(1L)).thenReturn(List.of());

        List<HabitProgress> result = getProgressUseCase.getProgressForUser(1L, null, null);

        assertThat(result).isEmpty();
        verify(timelineBuilder, never()).buildTimeline(anyList(), any(), any());
    }

}