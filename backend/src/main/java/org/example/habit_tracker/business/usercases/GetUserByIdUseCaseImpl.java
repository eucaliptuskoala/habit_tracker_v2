package org.example.habit_tracker.business.usercases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.users.User;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {

    private IUserRepository repository;

    @Override
    public User getUserById(Long id) {
        User user = repository.findById(id);
        if (user == null) {
            throw new UserNotFoundByIdException(id);
        }
        else{
            return user;
        }
    }
}