package org.example.habit_tracker.controller.mappers;

import org.example.habit_tracker.controller.dto.user.UserDto;
import org.example.habit_tracker.domain.users.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .isAdmin(user.isAdmin())
                .build();
    }
}