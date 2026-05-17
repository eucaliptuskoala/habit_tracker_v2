package org.example.habit_tracker.controller;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.checkin.ICreateCheckInUseCase;
import org.example.habit_tracker.business.checkin.IGetCheckInsForUserUseCase;
import org.example.habit_tracker.business.checkin.IUpdateCheckInUseCase;
import org.example.habit_tracker.business.checkin.fypstrategy.IGetForYouCheckInsUseCase;
import org.example.habit_tracker.configuration.security.UserIdProvider;
import org.example.habit_tracker.controller.dto.checkin.CheckInDto;
import org.example.habit_tracker.controller.dto.checkin.CreateCheckInRequest;
import org.example.habit_tracker.controller.dto.checkin.UpdateCheckInRequest;
import org.example.habit_tracker.controller.mappers.CheckInMapper;
import org.example.habit_tracker.domain.checkin.CheckIn;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/checkins")
@AllArgsConstructor
public class CheckInController {

    private ICreateCheckInUseCase createCheckInUseCase;
    private IGetCheckInsForUserUseCase getCheckInsForUserUseCase;
    private IUpdateCheckInUseCase updateCheckInUseCase;
    private IGetForYouCheckInsUseCase getForYouCheckInsUseCase;
    private CheckInMapper mapper;
    private UserIdProvider userIdProvider;

    @GetMapping
    public ResponseEntity<List<CheckInDto>> getCheckIns(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        Long userId = userIdProvider.getUserId();
        List<CheckIn> checkIns = getCheckInsForUserUseCase.getCheckInsForUser(userId, from, to);
        return ResponseEntity.ok(checkIns.stream().map(mapper::convertToDto).toList());
    }

    @PostMapping("/checkin")
    public ResponseEntity<CheckInDto> createCheckIn(@RequestBody CreateCheckInRequest request) {
        Long userId = userIdProvider.getUserId();
        CheckIn checkIn = createCheckInUseCase.createWithDetails(
                request.getHabitId(), request.getContent(), request.isPublic(), request.getMood());
        return ResponseEntity.ok(mapper.convertToDto(checkIn));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@checkInSecurity.isOwnerByEmail(#id, authentication.name)")
    public ResponseEntity<CheckInDto> updateCheckIn(
            @PathVariable Long id,
            @RequestBody UpdateCheckInRequest request
    ) {
        CheckIn checkIn = updateCheckInUseCase.update(id, request.getContent(), request.isPublic(), request.getMood());
        return ResponseEntity.ok(mapper.convertToDto(checkIn));
    }

    @GetMapping("/fyp")
    public ResponseEntity<List<CheckInDto>> getForYouCheckIns() {
        Long userId = userIdProvider.getUserId();
        List<CheckIn> checkIns = getForYouCheckInsUseCase.getForYouCheckIns(userId);
        return ResponseEntity.ok(checkIns.stream().map(mapper::convertToDto).toList());
    }
}
