package org.example.habit_tracker.business.categorycases;

import org.example.habit_tracker.business.exceptions.CategoryNotFoundByIdException;
import org.example.habit_tracker.business.repos.ICategoryRepository;
import org.example.habit_tracker.domain.habits.Category;
import org.springframework.stereotype.Service;

@Service
public class GetCategoryByIdUseCaseImpl implements IGetCategoryByIdUseCase {

    private final ICategoryRepository categoryRepository;

    public GetCategoryByIdUseCaseImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new CategoryNotFoundByIdException(id);
        }
        return category;
    }
}
