package org.solen.persistence.converters;

import org.solen.domain.users.User;
import org.solen.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public final class UserConverter {

    public UserEntity convertToEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .isAdmin(user.isAdmin())
                .build();
    }

    public User convertToDomain(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .isAdmin(userEntity.isAdmin())
                .build();
    }
}