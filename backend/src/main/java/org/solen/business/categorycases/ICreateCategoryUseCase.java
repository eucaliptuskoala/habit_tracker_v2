package org.example.habit_tracker.business.categorycases;

import org.example.habit_tracker.controller.dto.category.CreateCategoryRequest;
import org.example.habit_tracker.domain.habits.Category;

public interface ICreateCategoryUseCase {
    Category createCategory(CreateCategoryRequest request);
}
