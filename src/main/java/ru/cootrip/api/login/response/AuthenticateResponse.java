package ru.cootrip.api.login.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.cootrip.api.common.jwt.entity.AccessToken;
import ru.cootrip.api.common.jwt.entity.RefreshToken;

import java.time.Instant;

public class AuthenticateResponse {

    @JsonProperty
    private String accessToken;

    @JsonProperty
    private String refreshToken;

    @JsonProperty
    private Instant expiration;

    private AuthenticateResponse(AccessToken accessToken, RefreshToken refreshToken) {
        this.accessToken = accessToken.getToken();
        this.refreshToken = refreshToken.getToken();
        this.expiration = accessToken.getExpiration();
    }

    public static AuthenticateResponse create(AccessToken accessToken, RefreshToken refreshToken) {
        return new AuthenticateResponse(accessToken, refreshToken);
    }

}
