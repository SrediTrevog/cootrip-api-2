package ru.cootrip.api.internationalphonecode.exception;

public class InternationalPhoneCodeNotFoundException extends RuntimeException {

    private InternationalPhoneCodeNotFoundException(String message) {
        super(message);
    }

    public static InternationalPhoneCodeNotFoundException create(String message) {
        return new InternationalPhoneCodeNotFoundException(message);
    }

}
