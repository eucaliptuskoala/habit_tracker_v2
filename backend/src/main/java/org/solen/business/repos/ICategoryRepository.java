package org.solen.business.repos;

import org.solen.domain.habits.Category;

import java.util.List;

public interface ICategoryRepository {
    Category save(Category category);
    Category findById(Long id);
    List<Category> findAll();
    List<Category> findRootCategories();
    void deleteById(Long id);
    boolean existsByName(String name);
}
