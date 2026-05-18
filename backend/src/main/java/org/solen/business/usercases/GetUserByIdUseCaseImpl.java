package org.solen.business.usercases;

import lombok.AllArgsConstructor;
import org.solen.business.exceptions.UserNotFoundByIdException;
import org.solen.business.repos.IUserRepository;
import org.solen.domain.users.User;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {

    private IUserRepository repository;

    @Override
    public User getUserById(Long id) {
        User user = repository.findById(id);
        if (user == null) {
            throw new UserNotFoundByIdException(id);
        }
        else{
            return user;
        }
    }
}