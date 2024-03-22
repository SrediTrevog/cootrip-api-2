package ru.cootrip.api.airport.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.airport.exception.AirportAlreadyExistsException;
import ru.cootrip.api.airport.exception.AirportNotFoundException;

@ControllerAdvice
public class AirportExceptionHandler {

    @ExceptionHandler(AirportNotFoundException.class)
    public @ResponseBody ErrorResponse handleAirportNotFoundException(AirportNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AirportAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handleAirportAlreadyExistsException(AirportAlreadyExistsException e) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }

}
