package org.example.habit_tracker.configuration.security.ownership;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.domain.users.User;
import org.springframework.stereotype.Component;

@Component("categorySecurity")
@AllArgsConstructor
public class CategorySecurity {

    private IUserRepository userRepository;

    public boolean isAdminByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null && user.isAdmin();
    }
}
