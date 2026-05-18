package org.solen.business.categorycases;

import org.solen.controller.dto.category.CreateCategoryRequest;
import org.solen.domain.habits.Category;

public interface ICreateCategoryUseCase {
    Category createCategory(CreateCategoryRequest request);
}
