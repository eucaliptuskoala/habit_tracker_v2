package org.example.habit_tracker.business.categorycases;

import org.example.habit_tracker.domain.habits.Category;

public interface IGetCategoryByIdUseCase {
    Category getCategoryById(Long id);
}
