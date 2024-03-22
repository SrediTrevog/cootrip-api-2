package ru.cootrip.api.internationalphonecode.exception;

public class InternationalPhoneCodeAlreadyExistsException extends RuntimeException {

    private InternationalPhoneCodeAlreadyExistsException(String message) {
        super(message);
    }

    public static InternationalPhoneCodeAlreadyExistsException create(String message) {
        return new InternationalPhoneCodeAlreadyExistsException(message);
    }

}
