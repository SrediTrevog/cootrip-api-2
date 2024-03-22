package ru.cootrip.api.phonenumber.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.phonenumber.exception.PhoneNumberAlreadyExistsException;
import ru.cootrip.api.phonenumber.exception.PhoneNumberNotFoundException;

@ControllerAdvice
public class PhoneNumberExceptionHandler {

    @ExceptionHandler(PhoneNumberNotFoundException.class)
    public @ResponseBody ErrorResponse handlePhoneNumberNotFoundException(PhoneNumberNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(PhoneNumberAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handlePhoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException e) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }

}
