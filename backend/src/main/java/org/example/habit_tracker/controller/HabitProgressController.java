package org.example.habit_tracker.controller;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.habitprogress.IGetProgressForUserUseCase;
import org.example.habit_tracker.configuration.UserIdProvider;
import org.example.habit_tracker.controller.dto.progress.HabitProgressDto;
import org.example.habit_tracker.controller.mappers.HabitProgressMapper;
import org.example.habit_tracker.domain.progress.HabitProgress;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/habits_progress")
@AllArgsConstructor
public class HabitProgressController {

    private IGetProgressForUserUseCase getProgressForUserUseCase;
    private HabitProgressMapper mapper;
    private UserIdProvider userIdProvider;

    @GetMapping
    public ResponseEntity<List<HabitProgressDto>> getProgressForUser(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        Long userId = userIdProvider.getUserId();
        List<HabitProgress> progress = getProgressForUserUseCase.getProgressForUser(userId, from, to);
        return ResponseEntity.ok(progress.stream().map(mapper::convertToDto).toList());
    }
}