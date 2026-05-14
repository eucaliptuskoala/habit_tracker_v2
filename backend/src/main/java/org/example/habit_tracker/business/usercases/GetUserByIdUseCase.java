package org.example.habit_tracker.business.usercases;

import org.example.habit_tracker.domain.users.User;

public interface GetUserByIdUseCase {
    User getUserById(Long id);
}