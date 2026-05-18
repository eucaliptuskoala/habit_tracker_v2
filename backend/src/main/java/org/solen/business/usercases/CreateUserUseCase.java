package org.example.habit_tracker.business.usercases;

import org.example.habit_tracker.controller.dto.user.CreateUserRequest;
import org.example.habit_tracker.domain.users.User;

public interface CreateUserUseCase {
    User createUser(CreateUserRequest request);
}