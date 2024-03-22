package ru.cootrip.api.login.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.cootrip.api.common.jwt.entity.AccessToken;

import java.time.Instant;

public class RefreshTokenResponse {

    @JsonProperty
    private String accessToken;

    @JsonProperty
    private Instant expiration;

    private RefreshTokenResponse(AccessToken accessToken) {
        this.accessToken = accessToken.getToken();
        this.expiration = accessToken.getExpiration();
    }

    public static RefreshTokenResponse create(AccessToken accessToken) {
        return new RefreshTokenResponse(accessToken);
    }

}
