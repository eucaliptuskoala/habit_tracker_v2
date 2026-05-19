package org.solen.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.solen.business.checkin.IGetCheckInsForUserUseCase;
import org.solen.business.habitcases.ICreateHabitUseCase;
import org.solen.business.habitcases.IDeleteHabitUseCase;
import org.solen.business.habitcases.IGetHabitsByUserUseCase;
import org.solen.business.habitcases.IUpdateStreakUseCase;
import org.solen.configuration.security.UserIdProvider;
import org.solen.controller.dto.habit.CreateHabitRequest;
import org.solen.controller.dto.habit.HabitDto;
import org.solen.controller.mappers.HabitMapper;
import org.solen.domain.habits.Habit;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/habits")
@AllArgsConstructor
public class HabitController {

    private ICreateHabitUseCase createHabitUseCase;
    private IDeleteHabitUseCase deleteHabitUseCase;
    private IGetHabitsByUserUseCase getHabitsByUserUseCase;
    private IUpdateStreakUseCase updateStreakUseCase;
    private IGetCheckInsForUserUseCase getCheckInsForUserUseCase;
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
        Set<Long> checkedInTodayIds = getCheckInsForUserUseCase.findHabitIdsCheckedInTodayByUserId(userId);
        return ResponseEntity.ok(habits.stream()
                .map(h -> habitMapper.convertToDto(h, checkedInTodayIds))
                .toList());
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