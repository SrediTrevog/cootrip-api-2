package ru.cootrip.api.common.jwt.exception;

public class InvalidAccessTokenException extends RuntimeException {

    private InvalidAccessTokenException(String message) {
        super(message);
    }

    public static InvalidAccessTokenException create(String message) {
        return new InvalidAccessTokenException(message);
    }

}
