package org.example.habit_tracker.business.categorycases;

import org.example.habit_tracker.business.exceptions.CategoryNotFoundByIdException;
import org.example.habit_tracker.business.repos.ICategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteCategoryUseCaseImpl implements IDeleteCategoryUseCase {

    private final ICategoryRepository categoryRepository;

    public DeleteCategoryUseCaseImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void deleteCategory(Long id) {
        if (categoryRepository.findById(id) == null) {
            throw new CategoryNotFoundByIdException(id);
        }
        categoryRepository.deleteById(id);
    }
}
