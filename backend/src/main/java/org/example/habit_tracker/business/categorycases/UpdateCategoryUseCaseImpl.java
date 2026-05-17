package org.example.habit_tracker.business.categorycases;

import org.example.habit_tracker.business.exceptions.CategoryNotFoundByIdException;
import org.example.habit_tracker.business.repos.ICategoryRepository;
import org.example.habit_tracker.controller.dto.category.UpdateCategoryRequest;
import org.example.habit_tracker.domain.habits.Category;
import org.springframework.stereotype.Service;

@Service
public class UpdateCategoryUseCaseImpl implements IUpdateCategoryUseCase {

    private final ICategoryRepository categoryRepository;

    public UpdateCategoryUseCaseImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category updateCategory(Long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new CategoryNotFoundByIdException(id);
        }

        if (request.getName() != null) {
            category.setName(request.getName());
        }

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId());
            category.setParent(parent);
        } else if (request.isParentExplicitlyNull()) {
            category.setParent(null);
        }

        return categoryRepository.save(category);
    }
}
