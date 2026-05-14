package org.example.habit_tracker.business.repos;

import org.example.habit_tracker.domain.users.User;

import java.util.List;

public interface IUserRepository {
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    User save(User user);
    User findById(Long id);
    User findByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);
}