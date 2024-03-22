package ru.cootrip.api.country.exception;

public class CountryNotFoundException extends RuntimeException {

    private CountryNotFoundException(String message) {
        super(message);
    }

    public static CountryNotFoundException create(String message) {
        return new CountryNotFoundException(message);
    }

}
