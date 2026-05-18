package org.solen.business.checkin;

import org.solen.business.exceptions.HabitNotFoundByIdException;
import org.solen.business.repos.ICheckInRepository;
import org.solen.business.repos.IHabitRepository;
import org.solen.domain.checkin.CheckIn;
import org.solen.domain.checkin.Mood;
import org.solen.domain.habits.Habit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCheckInUseCaseImplTest {

    @Mock
    private ICheckInRepository checkInRepository;

    @Mock
    private IHabitRepository habitRepository;

    @InjectMocks
    private CreateCheckInUseCaseImpl createUseCase;

    @Test
    void createCheckIn_success() {
        Habit habit = Habit.builder()
                .id(1L)
                .name("Drink Water")
                .streak(10)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(7)
                .build();

        when(habitRepository.findById(1L)).thenReturn(habit);
        when(checkInRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CheckIn created = createUseCase.create(1L);

        assertNotNull(created);
        assertEquals(10, created.getStreakValue());
        assertNull(created.getContent());
        assertFalse(created.isPublic());
        assertNull(created.getMood());

        verify(habitRepository, times(1)).findById(1L);
        verify(checkInRepository, times(1)).save(any());
    }

    @Test
    void createCheckInWithDetails_success() {
        Habit habit = Habit.builder()
                .id(1L)
                .name("Meditation")
                .streak(8)
                .lastUpdatedStreak(LocalDateTime.now())
                .thresholdDays(5)
                .build();

        when(habitRepository.findById(1L)).thenReturn(habit);
        when(checkInRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CheckIn created = createUseCase.createWithDetails(1L, "Peaceful session", true, Mood.GOOD);

        assertNotNull(created);
        assertEquals(8, created.getStreakValue());
        assertEquals("Peaceful session", created.getContent());
        assertTrue(created.isPublic());
        assertEquals(Mood.GOOD, created.getMood());

        verify(habitRepository, times(1)).findById(1L);
        verify(checkInRepository, times(1)).save(any());
    }

    @Test
    void createCheckIn_habitNotFound() {
        when(habitRepository.findById(99L)).thenReturn(null);

        assertThrows(HabitNotFoundByIdException.class, () -> createUseCase.create(99L));
        verify(habitRepository, times(1)).findById(99L);
        verify(checkInRepository, never()).save(any());
    }
}
