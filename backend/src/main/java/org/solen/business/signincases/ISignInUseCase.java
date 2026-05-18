package org.example.habit_tracker.business.signincases;

import org.example.habit_tracker.controller.dto.auth.SignInRequest;
import org.example.habit_tracker.controller.dto.auth.SignInResponse;

public interface ISignInUseCase {
    SignInResponse signIn(SignInRequest signInRequest);
}