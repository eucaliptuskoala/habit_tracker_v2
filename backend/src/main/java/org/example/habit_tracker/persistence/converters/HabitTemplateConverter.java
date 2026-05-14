package org.example.habit_tracker.persistence.converters;

import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.example.habit_tracker.persistence.entities.HabitTemplateEntity;
import org.springframework.stereotype.Component;

@Component
public class HabitTemplateConverter {

    public HabitTemplateEntity convertToEntity(HabitTemplate template) {
        if(template == null){ return null; }
        return HabitTemplateEntity.builder()
                .id(template.getId())
                .name(template.getName())
                .popularity(template.getPopularity())
                .build();
    }

    public HabitTemplate convertToDomain(HabitTemplateEntity entity) {
        if(entity == null){ return null; }
        return HabitTemplate.builder()
                .id(entity.getId())
                .name(entity.getName())
                .popularity(entity.getPopularity())
                .build();
    }
}