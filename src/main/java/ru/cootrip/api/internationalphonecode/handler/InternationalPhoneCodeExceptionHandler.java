package ru.cootrip.api.internationalphonecode.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.internationalphonecode.exception.InternationalPhoneCodeAlreadyExistsException;
import ru.cootrip.api.internationalphonecode.exception.InternationalPhoneCodeNotFoundException;

@ControllerAdvice
public class InternationalPhoneCodeExceptionHandler {

    @ExceptionHandler(InternationalPhoneCodeNotFoundException.class)
    public @ResponseBody ErrorResponse handleInternationalPhoneCodeNotFoundException(
            InternationalPhoneCodeNotFoundException e
    ) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(InternationalPhoneCodeAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handleInternationalPhoneCodeAlreadyExistsException(
            InternationalPhoneCodeAlreadyExistsException e
    ) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }

}
