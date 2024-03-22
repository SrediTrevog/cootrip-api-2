package ru.cootrip.api.login.exception;

public class InvalidPasswordException extends RuntimeException {

    private InvalidPasswordException(String message) {
        super(message);
    }

    public static InvalidPasswordException create(String message) {
        return new InvalidPasswordException(message);
    }

}
