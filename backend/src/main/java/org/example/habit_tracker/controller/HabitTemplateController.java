package org.example.habit_tracker.controller;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.habittemplatecases.IGetAllHabitTemplatesUseCase;
import org.example.habit_tracker.business.habittemplatecases.IGetPopularHabitTemplates;
import org.example.habit_tracker.controller.dto.habit.HabitTemplateDto;
import org.example.habit_tracker.controller.mappers.HabitTemplateMapper;
import org.example.habit_tracker.domain.habits.HabitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit_templates")
@AllArgsConstructor
public class HabitTemplateController {

    private IGetAllHabitTemplatesUseCase getAllHabitTemplatesUseCase;
    private IGetPopularHabitTemplates  getPopularHabitTemplatesUseCase;
    private HabitTemplateMapper templateMapper;

    @GetMapping
    public ResponseEntity<List<HabitTemplateDto>> getAllHabitTemplates() {
        List<HabitTemplate> habitTemplates = getAllHabitTemplatesUseCase.getAllHabitTemplates();
        return ResponseEntity.ok(habitTemplates.stream().map(templateMapper::convertToDto).toList());
    }

    @GetMapping("/popular")
    public ResponseEntity<List<HabitTemplateDto>> getPopularHabitTemplates() {
        List<HabitTemplate> popularTemplates =  getPopularHabitTemplatesUseCase.getPopularHabitTemplates();
        return ResponseEntity.ok(popularTemplates.stream().map(templateMapper::convertToDto).toList());
    }
}