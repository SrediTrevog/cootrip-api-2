package ru.cootrip.api.city.exception;

public class CityNotFoundException extends RuntimeException {

    private CityNotFoundException(String message) {
        super(message);
    }

    public static CityNotFoundException create(String message) {
        return new CityNotFoundException(message);
    }

}
