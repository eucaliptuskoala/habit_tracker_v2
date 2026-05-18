package org.example.habit_tracker.business.categorycases;

import org.example.habit_tracker.controller.dto.category.CreateCategoryRequest;
import org.example.habit_tracker.domain.habits.Category;
import org.example.habit_tracker.business.repos.ICategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryUseCaseImpl implements ICreateCategoryUseCase {

    private final ICategoryRepository categoryRepository;

    public CreateCategoryUseCaseImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(CreateCategoryRequest request) {
        Category parent = request.getParentId() != null
                ? categoryRepository.findById(request.getParentId())
                : null;

        Category category = Category.builder()
                .name(request.getName())
                .parent(parent)
                .build();

        return categoryRepository.save(category);
    }
}
