package ru.cootrip.api.device.exception;

public class DeviceNotFoundException extends RuntimeException {

    private DeviceNotFoundException(String message) {
        super(message);
    }

    public static DeviceNotFoundException create(String message) {
        return new DeviceNotFoundException(message);
    }

}
