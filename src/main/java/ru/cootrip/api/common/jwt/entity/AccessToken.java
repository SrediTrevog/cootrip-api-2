package ru.cootrip.api.common.jwt.entity;

import lombok.Getter;

import java.time.Instant;

@Getter
public class AccessToken {

    private String token;

    private Instant expiration;

    private AccessToken(String token, Instant expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    public static AccessToken create(String token, Instant expiration) {
        return new AccessToken(token, expiration);
    }

}
