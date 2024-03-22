package ru.cootrip.api.phonenumber.exception;

public class PhoneNumberNotFoundException extends RuntimeException {

    private PhoneNumberNotFoundException(String message) {
        super(message);
    }

    public static PhoneNumberNotFoundException create(String message) {
        return new PhoneNumberNotFoundException(message);
    }

}
