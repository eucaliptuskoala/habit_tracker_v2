package org.example.habit_tracker.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="habits_progress",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"habit_id", "date"})
        }
)
public class HabitProgressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "habit_id", nullable = false)
    private HabitEntity habit;

    @Column(name="date", nullable = false)
    private LocalDate date;

    @Column(name = "streak_value", nullable = false)
    private int streakValue;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;
}