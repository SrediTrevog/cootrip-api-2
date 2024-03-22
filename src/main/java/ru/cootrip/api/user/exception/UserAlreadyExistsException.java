package ru.cootrip.api.user.exception;

public class UserAlreadyExistsException extends RuntimeException {

    private UserAlreadyExistsException(String message) {
        super(message);
    }

    public static UserAlreadyExistsException create(String message) {
        return new UserAlreadyExistsException(message);
    }

}
