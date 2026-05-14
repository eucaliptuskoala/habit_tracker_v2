package org.example.habit_tracker.business.usercases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final IUserRepository repository;

    @Override
    public void deleteUser(Long id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
        else {
            throw new UserNotFoundByIdException(id);
        }
    }
}