package ru.cootrip.api.city.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.city.exception.CityNotFoundException;

@ControllerAdvice
public class CityExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    public @ResponseBody ErrorResponse handleCityNotFoundException(CityNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

}
