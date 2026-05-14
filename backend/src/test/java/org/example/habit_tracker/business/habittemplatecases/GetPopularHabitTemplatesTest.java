package org.example.habit_tracker.business.habittemplatecases;

import org.example.habit_tracker.business.repos.IHabitTemplateRepository;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPopularHabitTemplatesTest {

    @Mock
    private IHabitTemplateRepository repository;

    @InjectMocks
    private GetPopularHabitTemplatesUseCaseImpl getPopularHabitTemplatesUseCaseImpl;

    List<HabitTemplate> habitTemplates;

    @BeforeEach
    void setUp() {
        habitTemplates = new ArrayList<>();

        HabitTemplate habitTemplate1 = HabitTemplate.builder()
                .id(1L)
                .name("Test1")
                .popularity(1)
                .build();
        habitTemplates.add(habitTemplate1);

        HabitTemplate habitTemplate2 = HabitTemplate.builder()
                .id(2L)
                .name("Test2")
                .popularity(2)
                .build();
        habitTemplates.add(habitTemplate2);

        HabitTemplate habitTemplate3 = HabitTemplate.builder()
                .id(3L)
                .name("Test3")
                .popularity(3)
                .build();
        habitTemplates.add(habitTemplate3);

        HabitTemplate habitTemplate4 = HabitTemplate.builder()
                .id(4L)
                .name("Test4")
                .popularity(4)
                .build();
        habitTemplates.add(habitTemplate4);

        HabitTemplate habitTemplate5 = HabitTemplate.builder()
                .id(5L)
                .name("Test5")
                .popularity(5)
                .build();
        habitTemplates.add(habitTemplate5);


        HabitTemplate habitTemplate6 = HabitTemplate.builder()
                .id(6L)
                .name("Test6")
                .popularity(6)
                .build();
        habitTemplates.add(habitTemplate6);

        HabitTemplate habitTemplate7 = HabitTemplate.builder()
                .id(7L)
                .name("Test7")
                .popularity(7)
                .build();
        habitTemplates.add(habitTemplate7);

        HabitTemplate habitTemplate8 = HabitTemplate.builder()
                .id(8L)
                .name("Test8")
                .popularity(8)
                .build();
        habitTemplates.add(habitTemplate8);

        HabitTemplate habitTemplate9 = HabitTemplate.builder()
                .id(9L)
                .name("Test9")
                .popularity(9)
                .build();
        habitTemplates.add(habitTemplate9);

        HabitTemplate habitTemplate10 = HabitTemplate.builder()
                .id(10L)
                .name("Test10")
                .popularity(10)
                .build();
        habitTemplates.add(habitTemplate10);

        HabitTemplate habitTemplate11 = HabitTemplate.builder()
                .id(11L)
                .name("Test11")
                .popularity(11)
                .build();
        habitTemplates.add(habitTemplate11);
    }

    @Test
    void getAllHabitTemplates_success() {

        when(repository.findAll()).thenReturn(habitTemplates);

        List<HabitTemplate> retrievedTemplates = getPopularHabitTemplatesUseCaseImpl.getPopularHabitTemplates();

        assertEquals(10, retrievedTemplates.size());
        assertEquals(retrievedTemplates.get(0), habitTemplates.get(10));
        assertEquals(retrievedTemplates.get(1), habitTemplates.get(9));

        verify(repository, times(1)).findAll();
    }
}