package ru.cootrip.api.airport.exception;

public class AirportAlreadyExistsException extends RuntimeException {

    private AirportAlreadyExistsException(String message) {
        super(message);
    }

    public static AirportAlreadyExistsException create(String message) {
        return new AirportAlreadyExistsException(message);
    }

}
