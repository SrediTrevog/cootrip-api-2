package ru.cootrip.api.phonenumber.exception;

public class PhoneNumberAlreadyExistsException extends RuntimeException {

    private PhoneNumberAlreadyExistsException(String message) {
        super(message);
    }

    public static PhoneNumberAlreadyExistsException create(String message) {
        return new PhoneNumberAlreadyExistsException(message);
    }

}
