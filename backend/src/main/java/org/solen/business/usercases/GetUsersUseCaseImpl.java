package org.example.habit_tracker.business.usercases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUsersUseCaseImpl implements GetUsersUseCase {

    private IUserRepository repository;

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }
}