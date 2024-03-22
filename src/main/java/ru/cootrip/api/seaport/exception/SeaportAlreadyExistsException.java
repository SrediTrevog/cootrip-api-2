package ru.cootrip.api.seaport.exception;

public class SeaportAlreadyExistsException extends RuntimeException {

    private SeaportAlreadyExistsException(String message) {
        super(message);
    }

    public static SeaportAlreadyExistsException create(String message) {
        return new SeaportAlreadyExistsException(message);
    }

}
