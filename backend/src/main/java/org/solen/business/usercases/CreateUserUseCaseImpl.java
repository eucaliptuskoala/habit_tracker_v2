package org.example.habit_tracker.business.usercases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.EmailAlreadyExistsException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.controller.dto.user.CreateUserRequest;
import org.example.habit_tracker.domain.users.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private IUserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(CreateUserRequest request) {

        if(request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        if(repository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        else{
            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .isAdmin(request.isAdmin())
                    .build();
            return repository.save(user);
        }
    }
}