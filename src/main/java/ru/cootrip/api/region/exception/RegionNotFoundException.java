package ru.cootrip.api.region.exception;

public class RegionNotFoundException extends RuntimeException {

    private RegionNotFoundException(String message) {
        super(message);
    }

    public static RegionNotFoundException create(String message) {
        return new RegionNotFoundException(message);
    }

}
