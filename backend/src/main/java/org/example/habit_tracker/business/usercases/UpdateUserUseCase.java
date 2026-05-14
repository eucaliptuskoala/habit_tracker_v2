package org.example.habit_tracker.business.usercases;

import org.example.habit_tracker.controller.dto.user.UpdateUserRequest;
import org.example.habit_tracker.domain.users.User;

public interface UpdateUserUseCase {
    User updateUser(UpdateUserRequest request, Long id);
}