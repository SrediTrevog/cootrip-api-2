package ru.cootrip.api.photo.exception;

public class PhotoAlreadyExistsException extends RuntimeException {

    private PhotoAlreadyExistsException(String message) {
        super(message);
    }

    public static PhotoAlreadyExistsException create(String message) {
        return new PhotoAlreadyExistsException(message);
    }

}
