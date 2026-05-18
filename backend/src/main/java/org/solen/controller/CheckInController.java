package org.example.habit_tracker.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.checkin.ICreateCheckInUseCase;
import org.example.habit_tracker.business.checkin.IDeleteCheckInUseCase;
import org.example.habit_tracker.business.checkin.IGetCheckInsForUserUseCase;
import org.example.habit_tracker.business.checkin.IUpdateCheckInUseCase;
import org.example.habit_tracker.business.checkin.fypstrategy.IGetForYouCheckInsUseCase;
import org.example.habit_tracker.configuration.security.UserIdProvider;
import org.example.habit_tracker.controller.dto.checkin.CheckInDto;
import org.example.habit_tracker.controller.dto.checkin.CreateCheckInRequest;
import org.example.habit_tracker.controller.dto.checkin.GetCheckInsDTO;
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
    private IDeleteCheckInUseCase deleteCheckInUseCase;
    private IGetForYouCheckInsUseCase getForYouCheckInsUseCase;
    private CheckInMapper mapper;
    private UserIdProvider userIdProvider;

    @GetMapping
    public ResponseEntity<List<CheckInDto>> getCheckIns(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        Long userId = userIdProvider.getUserId();
        GetCheckInsDTO dto = new GetCheckInsDTO(from, to);
        List<CheckIn> checkIns = getCheckInsForUserUseCase.getCheckInsForUser(userId, dto.getFrom(), dto.getTo());
        return ResponseEntity.ok(checkIns.stream().map(mapper::convertToDto).toList());
    }

    @PostMapping("/checkin")
    public ResponseEntity<CheckInDto> createCheckIn(@Valid @RequestBody CreateCheckInRequest request) {
        Long userId = userIdProvider.getUserId();
        CheckIn checkIn = createCheckInUseCase.createWithDetails(
                request.getHabitId(), request.getContent(), request.isPublic(), request.getMood());
        return ResponseEntity.ok(mapper.convertToDto(checkIn));
    }

    @PutMapping("/{id}")
    @PreAuthorize("@checkInSecurity.isOwnerByEmail(#id, authentication.name)")
    public ResponseEntity<CheckInDto> updateCheckIn(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCheckInRequest request
    ) {
        CheckIn checkIn = updateCheckInUseCase.update(id, request.getContent(), request.isPublic(), request.getMood());
        return ResponseEntity.ok(mapper.convertToDto(checkIn));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@checkInSecurity.isOwnerByEmail(#id, authentication.name)")
    public ResponseEntity<Void> deleteCheckIn(@PathVariable Long id) {
        deleteCheckInUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fyp")
    public ResponseEntity<List<CheckInDto>> getForYouCheckIns() {
        Long userId = userIdProvider.getUserId();
        List<CheckIn> checkIns = getForYouCheckInsUseCase.getForYouCheckIns(userId);
        return ResponseEntity.ok(checkIns.stream().map(mapper::convertToDto).toList());
    }
}
