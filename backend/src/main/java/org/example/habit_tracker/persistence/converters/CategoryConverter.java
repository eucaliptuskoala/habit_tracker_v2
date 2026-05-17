package org.example.habit_tracker.persistence.converters;

import org.example.habit_tracker.domain.habits.Category;
import org.example.habit_tracker.persistence.entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverter {

    public CategoryEntity convertToEntity(Category category) {
        if (category == null) return null;
        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .parent(category.getParent() != null ? convertToEntity(category.getParent()) : null)
                .build();
    }

    public Category convertToDomain(CategoryEntity entity) {
        if (entity == null) return null;
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .parent(entity.getParent() != null ? convertToDomain(entity.getParent()) : null)
                .build();
    }

    public Category convertToDomainWithChildren(CategoryEntity entity) {
        if (entity == null) return null;
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .parent(entity.getParent() != null ? convertToDomain(entity.getParent()) : null)
                .children(entity.getChildren() != null
                        ? entity.getChildren().stream().map(this::convertToDomain).toList()
                        : null)
                .build();
    }
}
