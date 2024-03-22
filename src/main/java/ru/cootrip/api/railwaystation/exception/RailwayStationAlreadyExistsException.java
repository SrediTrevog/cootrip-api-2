package ru.cootrip.api.railwaystation.exception;

public class RailwayStationAlreadyExistsException extends RuntimeException {

    private RailwayStationAlreadyExistsException(String message) {
        super(message);
    }

    public static RailwayStationAlreadyExistsException create(String message) {
        return new RailwayStationAlreadyExistsException(message);
    }

}
