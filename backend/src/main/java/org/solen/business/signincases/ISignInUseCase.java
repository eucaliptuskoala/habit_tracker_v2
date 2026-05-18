package org.solen.business.signincases;

import org.solen.controller.dto.auth.SignInRequest;
import org.solen.controller.dto.auth.SignInResponse;

public interface ISignInUseCase {
    SignInResponse signIn(SignInRequest signInRequest);
}