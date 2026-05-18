package org.example.habit_tracker.business.usercases;

import org.example.habit_tracker.domain.users.User;

import java.util.List;

public interface GetUsersUseCase {
    List<User> getUsers();
}