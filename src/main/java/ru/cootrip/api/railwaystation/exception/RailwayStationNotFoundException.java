package ru.cootrip.api.railwaystation.exception;

public class RailwayStationNotFoundException extends RuntimeException {

    private RailwayStationNotFoundException(String message) {
        super(message);
    }

    public static RailwayStationNotFoundException create(String message) {
        return new RailwayStationNotFoundException(message);
    }

}
