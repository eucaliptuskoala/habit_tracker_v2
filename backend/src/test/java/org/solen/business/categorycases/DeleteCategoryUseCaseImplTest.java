package org.example.habit_tracker.business.categorycases;

import org.example.habit_tracker.business.exceptions.CategoryNotFoundByIdException;
import org.example.habit_tracker.business.repos.ICategoryRepository;
import org.example.habit_tracker.domain.habits.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCategoryUseCaseImplTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private DeleteCategoryUseCaseImpl deleteCategoryUseCase;

    @Test
    void deleteCategory_success() {
        when(categoryRepository.findById(1L)).thenReturn(Category.builder().id(1L).build());

        deleteCategoryUseCase.deleteCategory(1L);

        verify(categoryRepository).deleteById(1L);
    }

    @Test
    void deleteCategory_notFound() {
        when(categoryRepository.findById(99L)).thenReturn(null);

        assertThrows(CategoryNotFoundByIdException.class, () -> deleteCategoryUseCase.deleteCategory(99L));
        verify(categoryRepository, never()).deleteById(any());
    }
}
