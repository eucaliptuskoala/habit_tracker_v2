package org.example.habit_tracker.controller.mappers;

import org.example.habit_tracker.controller.dto.habit.HabitTemplateDto;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.springframework.stereotype.Component;

@Component
public class HabitTemplateMapper {

    public HabitTemplateDto convertToDto(HabitTemplate template) {
        if(template == null){ return null;}
        return HabitTemplateDto.builder()
                .id(template.getId())
                .name(template.getName())
                .popularity(template.getPopularity())
                .build();
    }
}