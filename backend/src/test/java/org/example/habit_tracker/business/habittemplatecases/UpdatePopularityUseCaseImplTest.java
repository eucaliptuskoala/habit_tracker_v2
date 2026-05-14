package org.example.habit_tracker.business.habittemplatecases;

import org.example.habit_tracker.business.repos.IHabitTemplateRepository;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePopularityUseCaseImplTest {

    @Mock
    private IHabitTemplateRepository repository;

    @InjectMocks
    private UpdatePopularityUseCaseImpl updatePopularityUseCaseImpl;

    @Test
    void updatePopularity_success() {
        HabitTemplate habitTemplate = HabitTemplate.builder()
                .id(1L)
                .name("Test1")
                .popularity(1)
                .build();

        when(repository.findById(1L)).thenReturn(habitTemplate);

        updatePopularityUseCaseImpl.updatePopularity(1L);

        verify(repository, times(1)).findById(1L);
        assertEquals(2, habitTemplate.getPopularity());
    }

}