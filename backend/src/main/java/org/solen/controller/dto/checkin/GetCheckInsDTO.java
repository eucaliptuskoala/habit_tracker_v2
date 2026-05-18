package org.example.habit_tracker.controller.dto.checkin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCheckInsDTO {
    private LocalDate from;
    private LocalDate to;
}
