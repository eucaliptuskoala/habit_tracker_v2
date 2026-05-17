package org.example.habit_tracker.controller.mappers;

import org.example.habit_tracker.controller.dto.category.CategoryResponse;
import org.example.habit_tracker.domain.habits.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse convertToResponse(Category category) {
        if (category == null) return null;
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .children(category.getChildren() != null
                        ? category.getChildren().stream().map(this::convertToResponse).toList()
                        : null)
                .build();
    }
}
