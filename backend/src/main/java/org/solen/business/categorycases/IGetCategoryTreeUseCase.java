package org.solen.business.categorycases;

import org.solen.domain.habits.Category;

import java.util.List;

public interface IGetCategoryTreeUseCase {
    List<Category> getCategoryTree();
}
