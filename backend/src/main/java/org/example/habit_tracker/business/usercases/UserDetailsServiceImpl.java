package org.example.habit_tracker.business.usercases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.UserNotFoundByEmailException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.users.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private IUserRepository repository;

    @Override
    public UserDetails loadByEmail(String email) {

        User user = repository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundByEmailException(email);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.isAdmin() ? "ADMIN" : "USER")
                .build();
    }
}