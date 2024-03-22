package ru.cootrip.api.country.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.country.exception.CountryAlreadyExistsException;
import ru.cootrip.api.country.exception.CountryNotFoundException;

@ControllerAdvice
public class CountryExceptionHandler {

    @ExceptionHandler(CountryNotFoundException.class)
    public @ResponseBody ErrorResponse handleCountryNotFoundException(CountryNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(CountryAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handleCountryAlreadyExistsException(CountryAlreadyExistsException e) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }

}
