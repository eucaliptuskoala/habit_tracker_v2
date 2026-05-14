package org.example.habit_tracker.business.usercases;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    UserDetails loadByEmail(String email);
}