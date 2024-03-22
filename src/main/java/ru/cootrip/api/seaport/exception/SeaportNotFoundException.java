package ru.cootrip.api.seaport.exception;

public class SeaportNotFoundException extends RuntimeException {

    private SeaportNotFoundException(String message) {
        super(message);
    }

    public static SeaportNotFoundException create(String message) {
        return new SeaportNotFoundException(message);
    }

}
