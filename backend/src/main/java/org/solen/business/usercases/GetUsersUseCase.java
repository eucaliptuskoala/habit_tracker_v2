package org.solen.business.usercases;

import org.solen.domain.users.User;

import java.util.List;

public interface GetUsersUseCase {
    List<User> getUsers();
}