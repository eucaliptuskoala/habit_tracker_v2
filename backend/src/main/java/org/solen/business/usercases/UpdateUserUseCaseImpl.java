package org.example.habit_tracker.business.usercases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.UserNotFoundByIdException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.controller.dto.user.UpdateUserRequest;
import org.example.habit_tracker.domain.users.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private IUserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User updateUser(UpdateUserRequest request, Long id) {

        User user = repository.findById(id);

        if (user == null) {
            throw new UserNotFoundByIdException(id);
        }

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return repository.save(user);
    }
}