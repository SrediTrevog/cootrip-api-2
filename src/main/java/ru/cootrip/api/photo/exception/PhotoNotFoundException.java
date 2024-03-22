package ru.cootrip.api.photo.exception;

public class PhotoNotFoundException extends RuntimeException {

    private PhotoNotFoundException(String message) {
        super(message);
    }

    public static PhotoNotFoundException create(String message) {
        return new PhotoNotFoundException(message);
    }

}
