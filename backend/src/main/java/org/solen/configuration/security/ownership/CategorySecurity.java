package org.solen.configuration.security.ownership;

import lombok.AllArgsConstructor;
import org.solen.business.repos.IUserRepository;
import org.solen.domain.users.User;
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
