package ru.cootrip.api.country.exception;

public class CountryAlreadyExistsException extends RuntimeException {

    private CountryAlreadyExistsException(String message) {
        super(message);
    }

    public static CountryAlreadyExistsException create(String message) {
        return new CountryAlreadyExistsException(message);
    }

}
