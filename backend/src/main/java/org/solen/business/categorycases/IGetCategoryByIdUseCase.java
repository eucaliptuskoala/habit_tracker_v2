package org.solen.business.categorycases;

import org.solen.domain.habits.Category;

public interface IGetCategoryByIdUseCase {
    Category getCategoryById(Long id);
}
