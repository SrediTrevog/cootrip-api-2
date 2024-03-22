package ru.cootrip.api.authority.exception;

public class AuthorityAlreadyExistsException extends RuntimeException {

    private AuthorityAlreadyExistsException(String message) {
        super(message);
    }

    public static AuthorityAlreadyExistsException create(String message) {
        return new AuthorityAlreadyExistsException(message);
    }

}
