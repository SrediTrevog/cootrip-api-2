package ru.cootrip.api.user.exception;

public class UserNotFoundException extends RuntimeException {

    private UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException create(String message) {
        return new UserNotFoundException(message);
    }

}
