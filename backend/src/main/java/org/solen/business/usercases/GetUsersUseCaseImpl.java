package org.solen.business.usercases;

import lombok.AllArgsConstructor;
import org.solen.business.repos.IUserRepository;
import org.solen.domain.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetUsersUseCaseImpl implements GetUsersUseCase {

    private IUserRepository repository;

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }
}