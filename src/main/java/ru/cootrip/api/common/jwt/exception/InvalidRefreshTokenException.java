package ru.cootrip.api.common.jwt.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    private InvalidRefreshTokenException(String message) {
        super(message);
    }

    public static InvalidRefreshTokenException create(String message) {
        return new InvalidRefreshTokenException(message);
    }

}
