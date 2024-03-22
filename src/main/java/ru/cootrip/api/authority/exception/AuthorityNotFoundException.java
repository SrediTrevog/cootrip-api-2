package ru.cootrip.api.authority.exception;

public class AuthorityNotFoundException extends RuntimeException {

    private AuthorityNotFoundException(String message) {
        super(message);
    }

    public static AuthorityNotFoundException create(String message) {
        return new AuthorityNotFoundException(message);
    }

}
