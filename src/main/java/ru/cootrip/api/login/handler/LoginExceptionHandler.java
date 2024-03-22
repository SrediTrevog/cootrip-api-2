package ru.cootrip.api.login.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.common.jwt.exception.InvalidRefreshTokenException;
import ru.cootrip.api.login.exception.InvalidPasswordException;

@ControllerAdvice
public class LoginExceptionHandler {

    @ExceptionHandler(InvalidPasswordException.class)
    public @ResponseBody ErrorResponse handleInvalidPasswordException(InvalidPasswordException e) {
        return ErrorResponse.create(e, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public @ResponseBody ErrorResponse handleInvalidRefreshTokenException(InvalidRefreshTokenException e) {
        return ErrorResponse.create(e, HttpStatus.UNAUTHORIZED, e.getMessage());
    }

}
