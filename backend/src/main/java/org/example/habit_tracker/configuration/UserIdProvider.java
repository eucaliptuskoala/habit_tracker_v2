package org.example.habit_tracker.configuration;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.users.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserIdProvider {

    private IUserRepository userRepository;

    public Long getUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        return user.getId();
    }
}