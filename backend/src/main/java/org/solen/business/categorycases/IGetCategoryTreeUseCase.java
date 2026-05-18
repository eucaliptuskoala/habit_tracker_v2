package org.example.habit_tracker.business.categorycases;

import org.example.habit_tracker.domain.habits.Category;

import java.util.List;

public interface IGetCategoryTreeUseCase {
    List<Category> getCategoryTree();
}
