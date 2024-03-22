package ru.cootrip.api.airport.exception;

public class AirportNotFoundException extends RuntimeException {

    private AirportNotFoundException(String message) {
        super(message);
    }

    public static AirportNotFoundException create(String message) {
        return new AirportNotFoundException(message);
    }

}
