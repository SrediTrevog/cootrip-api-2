package ru.cootrip.api.login.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.cootrip.api.otp.entity.OTP;

import java.util.UUID;

public class LoginResponse {

    @JsonProperty
    private UUID otpId;

    private LoginResponse(OTP entity) {
        this.otpId = entity.getId();
    }

    public static LoginResponse create(OTP entity) {
        return new LoginResponse(entity);
    }

}
