package org.example.habit_tracker.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.habitcases.ICreateHabitUseCase;
import org.example.habit_tracker.business.habitcases.IDeleteHabitUseCase;
import org.example.habit_tracker.business.habitcases.IGetHabitsByUserUseCase;
import org.example.habit_tracker.business.habitcases.IUpdateStreakUseCase;
import org.example.habit_tracker.configuration.security.UserIdProvider;
import org.example.habit_tracker.controller.dto.habit.CreateHabitRequest;
import org.example.habit_tracker.controller.dto.habit.HabitDto;
import org.example.habit_tracker.controller.mappers.HabitMapper;
import org.example.habit_tracker.domain.habits.Habit;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habits")
@AllArgsConstructor
public class HabitController {

    private ICreateHabitUseCase createHabitUseCase;
    private IDeleteHabitUseCase deleteHabitUseCase;
    private IGetHabitsByUserUseCase getHabitsByUserUseCase;
    private IUpdateStreakUseCase updateStreakUseCase;
    private HabitMapper habitMapper;
    private UserIdProvider userIdProvider;

    @PostMapping
    public ResponseEntity<HabitDto> createHabit(@Valid @RequestBody CreateHabitRequest request){
        Long userId = userIdProvider.getUserId();
        Habit habit = createHabitUseCase.createHabit(request, userId);
        return ResponseEntity.ok(habitMapper.convertToDto(habit));
    }

    @GetMapping("/my")
    public ResponseEntity<List<HabitDto>> getHabitsByUser(){
        Long userId = userIdProvider.getUserId();
        List<Habit> habits = getHabitsByUserUseCase.getHabitsByUser(userId);
        return ResponseEntity.ok(habits.stream().map(habitMapper::convertToDto).toList());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@habitSecurity.isOwnerByEmail(#id, authentication.name)")
    public ResponseEntity<Void> deleteHabit(@PathVariable Long id){
        deleteHabitUseCase.deleteHabit(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("@habitSecurity.isOwnerByEmail(#id, authentication.name)")
    public ResponseEntity<HabitDto> updateStreak(@PathVariable Long id){
        Habit updatedHabit = updateStreakUseCase.updateStreak(id);
        return ResponseEntity.ok(habitMapper.convertToDto(updatedHabit));
    }
}