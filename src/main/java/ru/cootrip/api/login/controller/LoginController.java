package ru.cootrip.api.login.controller;

import jakarta.validation.Valid;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.cootrip.api.common.jwt.entity.AccessToken;
import ru.cootrip.api.common.jwt.entity.RefreshToken;
import ru.cootrip.api.common.jwt.exception.InvalidRefreshTokenException;
import ru.cootrip.api.login.exception.InvalidPasswordException;
import ru.cootrip.api.login.request.AuthenticateRequest;
import ru.cootrip.api.login.request.LoginRequest;
import ru.cootrip.api.login.request.RefreshTokenRequest;
import ru.cootrip.api.login.response.AuthenticateResponse;
import ru.cootrip.api.login.response.LoginResponse;
import ru.cootrip.api.login.response.RefreshTokenResponse;
import ru.cootrip.api.login.service.LoginService;
import ru.cootrip.api.otp.entity.OTP;
import ru.cootrip.api.otp.exception.OTPNotFoundException;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

//    Тут JSON
//    {
//        "phone" :"+79521234567"
//    }
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody LoginResponse login(
            @RequestBody @Valid LoginRequest request
    ) {
        final OTP otp = loginService.login(request.getPhone());
        return LoginResponse.create(otp);
    }

//    Для этого говна такой JSON
//    {
//            "otpId": "550e8400-e29b-41d4-a716-446655440000",
//            "password": "Pass12",
//            "deviceName": "MyPhone",
//            "deviceInfo": "Model: XYZ, OS: ANDROID 10",
//            "devicePlatform": "ANDROID"
//    }
    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody AuthenticateResponse authenticate(
            @RequestBody @Valid AuthenticateRequest request
    ) throws OTPNotFoundException, InvalidPasswordException {
        final Pair<AccessToken, RefreshToken> tokens = loginService.authenticate(
                request.getOtpId(),
                request.getPassword(),
                request.getDeviceName(),
                request.getDeviceInfo(),
                request.getDevicePlatform()
        );

        return AuthenticateResponse.create(tokens.a, tokens.b);
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody RefreshTokenResponse refreshToken(
            @RequestBody @Valid RefreshTokenRequest request
    ) throws InvalidRefreshTokenException {
        final AccessToken accessToken = loginService.refreshToken(request.getRefreshToken());
        return RefreshTokenResponse.create(accessToken);
    }

}
