package ru.cootrip.api.railwaystation.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.railwaystation.exception.RailwayStationAlreadyExistsException;
import ru.cootrip.api.railwaystation.exception.RailwayStationNotFoundException;

@ControllerAdvice
public class RailwayStationExceptionHandler {

    @ExceptionHandler(RailwayStationNotFoundException.class)
    public @ResponseBody ErrorResponse handleRailwayStationNotFoundException(RailwayStationNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(RailwayStationAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handleRailwayStationAlreadyExistsException(RailwayStationAlreadyExistsException e) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }

}
