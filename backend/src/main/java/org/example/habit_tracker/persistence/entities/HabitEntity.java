package org.example.habit_tracker.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="habits")
public class HabitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "name", nullable = false)
    private String name;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name = "streak", nullable = false)
    private int streak;

    @Column(name="last_updated_streak")
    private LocalDateTime lastUpdatedStreak;

    @Column(name="threshold_days", nullable = false)
    private int thresholdDays;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_template_id")
    private HabitTemplateEntity template;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    private UserEntity creator;
}