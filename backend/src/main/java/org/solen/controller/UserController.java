package org.solen.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.solen.business.usercases.*;
import org.solen.controller.dto.user.CreateUserRequest;
import org.solen.controller.dto.user.UpdateUserRequest;
import org.solen.controller.dto.user.UserDto;
import org.solen.controller.mappers.UserMapper;
import org.solen.domain.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private CreateUserUseCase createUserUseCase;
    private DeleteUserUseCase deleteUserUseCase;
    private GetUsersUseCase getUsersUseCase;
    private GetUserByIdUseCase getUserByIdUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest request) {
        User user = createUserUseCase.createUser(request);
        return ResponseEntity.ok(mapper.convertToDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        deleteUserUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = getUsersUseCase.getUsers();
        return ResponseEntity.ok(users.stream().map(mapper::convertToDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = getUserByIdUseCase.getUserById(id);
        return ResponseEntity.ok(mapper.convertToDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        User user = updateUserUseCase.updateUser(request, id);
        return ResponseEntity.ok(mapper.convertToDto(user));
    }
}