package ru.cootrip.api.otp.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.otp.exception.OTPAlreadyExistsException;
import ru.cootrip.api.otp.exception.OTPNotFoundException;

@ControllerAdvice
public class OTPExceptionHandler {

    @ExceptionHandler(OTPNotFoundException.class)
    public @ResponseBody ErrorResponse handleOtpNotFoundException(OTPNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(OTPAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handleOtpAlreadyExistsException(OTPAlreadyExistsException e) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }

}
