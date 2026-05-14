package org.example.habit_tracker.controller;

import lombok.AllArgsConstructor;
import org.example.habit_tracker.business.signincases.ISignInUseCase;
import org.example.habit_tracker.controller.dto.auth.SignInRequest;
import org.example.habit_tracker.controller.dto.auth.SignInResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private ISignInUseCase signInUseCase;

    @PostMapping("/sign_in")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request) {
        SignInResponse token = signInUseCase.signIn(request);
        return ResponseEntity.ok(token);
    }
}
