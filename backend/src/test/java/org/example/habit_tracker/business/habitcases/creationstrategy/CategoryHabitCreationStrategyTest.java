package org.example.habit_tracker.business.habitcases.creationstrategy;

import org.example.habit_tracker.business.exceptions.CategoryNotFoundByIdException;
import org.example.habit_tracker.business.exceptions.HabitAlreadyExistsException;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.ICategoryRepository;
import org.example.habit_tracker.business.repos.IHabitRepository;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
import org.example.habit_tracker.domain.habits.Category;
import org.example.habit_tracker.domain.habits.Habit;
import org.example.habit_tracker.domain.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryHabitCreationStrategyTest {

    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private IHabitRepository habitRepository;
    @Mock
    private IUserRepository userRepository;

    @InjectMocks
    private CategoryHabitCreationStrategy strategy;

    private User user;
    private Category category;
    private CreateHabitRequest request;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("User")
                .email("user@test.com")
                .password("password")
                .isAdmin(false)
                .build();

        category = Category.builder()
                .id(1L)
                .name("Fitness")
                .build();

        request = CreateHabitRequest.builder()
                .categoryId(1L)
                .name("  morning workout  ")
                .description("Daily morning exercise")
                .build();
    }

    @Test
    void createHabit_success() {
        when(categoryRepository.findById(1L)).thenReturn(category);
        when(userRepository.findById(1L)).thenReturn(user);
        when(habitRepository.findByCreatorId(1L)).thenReturn(List.of());
        when(habitRepository.save(any(Habit.class))).thenAnswer(i -> i.getArgument(0));

        Habit result = strategy.createHabit(request, 1L);

        assertNotNull(result);
        assertEquals("Morning workout", result.getName());
        assertEquals("Daily morning exercise", result.getDescription());
        assertEquals(category, result.getCategory());
        assertEquals(user, result.getCreator());
        assertEquals(0, result.getStreak());
        assertEquals(1, result.getThresholdDays());

        verify(categoryRepository).findById(1L);
        verify(habitRepository).save(any(Habit.class));
    }

    @Test
    void createHabit_categoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(null);

        assertThrows(CategoryNotFoundByIdException.class, () -> strategy.createHabit(request, 1L));
        verify(habitRepository, never()).save(any());
    }

    @Test
    void createHabit_userNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(category);
        when(userRepository.findById(1L)).thenReturn(null);

        assertThrows(UserNotFoundByIdException.class, () -> strategy.createHabit(request, 1L));
        verify(habitRepository, never()).save(any());
    }

    @Test
    void createHabit_duplicateName() {
        Habit existing = Habit.builder().name("Morning workout").build();

        when(categoryRepository.findById(1L)).thenReturn(category);
        when(userRepository.findById(1L)).thenReturn(user);
        when(habitRepository.findByCreatorId(1L)).thenReturn(List.of(existing));

        assertThrows(HabitAlreadyExistsException.class, () -> strategy.createHabit(request, 1L));
        verify(habitRepository, never()).save(any());
    }

    @Test
    void normalizeName_trimsAndCapitalizes() {
        assertEquals("Hello", CategoryHabitCreationStrategy.normalizeName("  hello  "));
        assertEquals("World", CategoryHabitCreationStrategy.normalizeName("WORLD"));
        assertEquals("Test", CategoryHabitCreationStrategy.normalizeName("test"));
    }
}
