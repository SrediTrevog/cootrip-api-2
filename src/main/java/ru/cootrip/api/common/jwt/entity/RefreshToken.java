package ru.cootrip.api.common.jwt.entity;

import lombok.Getter;

@Getter
public class RefreshToken {

    private String token;

    private RefreshToken(String token) {
        this.token = token;
    }

    public static RefreshToken create(String token) {
        return new RefreshToken(token);
    }

}
