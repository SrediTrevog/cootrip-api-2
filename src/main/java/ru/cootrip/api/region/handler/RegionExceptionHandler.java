package ru.cootrip.api.region.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.region.exception.RegionNotFoundException;

@ControllerAdvice
public class RegionExceptionHandler {

    @ExceptionHandler(RegionNotFoundException.class)
    public @ResponseBody ErrorResponse handleRegionNotFoundException(RegionNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

}
