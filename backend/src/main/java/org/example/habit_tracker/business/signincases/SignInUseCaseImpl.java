package org.example.habit_tracker.business.signincases;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.exceptions.UserNotFoundByEmailException;
import org.example.habit_tracker.business.repos.IUserRepository;
import org.example.habit_tracker.configuration.JwtUtil;
import org.example.habit_tracker.controller.dto.auth.SignInRequest;
import org.example.habit_tracker.controller.dto.auth.SignInResponse;
import org.example.habit_tracker.domain.users.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SignInUseCaseImpl implements ISignInUseCase {

    private IUserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByEmail(signInRequest.getEmail());

        if (user == null || !passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new UserNotFoundByEmailException(signInRequest.getEmail());
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getId());

        return SignInResponse.builder().token(token).build();
    }
}