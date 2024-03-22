package ru.cootrip.api.photo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.photo.exception.PhotoAlreadyExistsException;
import ru.cootrip.api.photo.exception.PhotoNotFoundException;

@ControllerAdvice
public class PhotoExceptionHandler {

    @ExceptionHandler(PhotoNotFoundException.class)
    public @ResponseBody ErrorResponse handlePhotoNotFoundException(PhotoNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(PhotoAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handlePhotoAlreadyExistsException(PhotoAlreadyExistsException e) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }

}
