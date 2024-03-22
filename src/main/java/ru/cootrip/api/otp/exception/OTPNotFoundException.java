package ru.cootrip.api.otp.exception;

public class OTPNotFoundException extends RuntimeException {

    private OTPNotFoundException(String message) {
        super(message);
    }

    public static OTPNotFoundException create(String message) {
        return new OTPNotFoundException(message);
    }

}
