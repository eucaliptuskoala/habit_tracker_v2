package org.example.habit_tracker.business.repos;

import org.example.habit_tracker.domain.habits.Category;

import java.util.List;

public interface ICategoryRepository {
    Category save(Category category);
    Category findById(Long id);
    List<Category> findAll();
    List<Category> findRootCategories();
    void deleteById(Long id);
    boolean existsByName(String name);
}
