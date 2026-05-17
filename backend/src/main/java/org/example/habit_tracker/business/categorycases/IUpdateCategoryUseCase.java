package org.example.habit_tracker.business.categorycases;

import org.example.habit_tracker.controller.dto.category.UpdateCategoryRequest;
import org.example.habit_tracker.domain.habits.Category;

public interface IUpdateCategoryUseCase {
    Category updateCategory(Long id, UpdateCategoryRequest request);
}
