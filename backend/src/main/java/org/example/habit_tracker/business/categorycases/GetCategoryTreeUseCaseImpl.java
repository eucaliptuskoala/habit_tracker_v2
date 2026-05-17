package org.example.habit_tracker.business.categorycases;

import org.example.habit_tracker.business.repos.ICategoryRepository;
import org.example.habit_tracker.domain.habits.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCategoryTreeUseCaseImpl implements IGetCategoryTreeUseCase {

    private final ICategoryRepository categoryRepository;

    public GetCategoryTreeUseCaseImpl(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategoryTree() {
        return categoryRepository.findRootCategories();
    }
}
