package org.solen.persistence.converters;

import lombok.AllArgsConstructor;
import org.solen.domain.checkin.CheckIn;
import org.solen.persistence.entities.CheckInEntity;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CheckInConverter {

    private HabitConverter habitConverter;

    public CheckInEntity convertToEntity(CheckIn checkIn) {
        return CheckInEntity.builder()
                .id(checkIn.getId())
                .habit(habitConverter.convertToEntity(checkIn.getHabit()))
                .date(checkIn.getDate())
                .streakValue(checkIn.getStreakValue())
                .content(checkIn.getContent())
                .isPublic(checkIn.isPublic())
                .mood(checkIn.getMood())
                .createdAt(checkIn.getCreatedAt())
                .build();
    }

    public CheckIn convertToDomain(CheckInEntity entity) {
        return CheckIn.builder()
                .id(entity.getId())
                .habit(habitConverter.convertToDomain(entity.getHabit()))
                .date(entity.getDate())
                .streakValue(entity.getStreakValue())
                .content(entity.getContent())
                .isPublic(entity.isPublic())
                .mood(entity.getMood())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
