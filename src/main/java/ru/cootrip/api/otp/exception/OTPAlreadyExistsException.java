package ru.cootrip.api.otp.exception;

public class OTPAlreadyExistsException extends RuntimeException {

    private OTPAlreadyExistsException(String message) {
        super(message);
    }

    public static OTPAlreadyExistsException create(String message) {
        return new OTPAlreadyExistsException(message);
    }

}
