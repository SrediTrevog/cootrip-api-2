package ru.cootrip.api.seaport.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.seaport.exception.SeaportAlreadyExistsException;
import ru.cootrip.api.seaport.exception.SeaportNotFoundException;

@ControllerAdvice
public class SeaportExceptionHandler {

    @ExceptionHandler(SeaportNotFoundException.class)
    public @ResponseBody ErrorResponse handleSeaportNotFoundException(SeaportNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(SeaportAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handleSeaportAlreadyExistsException(SeaportAlreadyExistsException e) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }

}
