package ru.cootrip.api.authority.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.authority.exception.AuthorityAlreadyExistsException;
import ru.cootrip.api.authority.exception.AuthorityNotFoundException;

@ControllerAdvice
public class AuthorityExceptionHandler {

    @ExceptionHandler(AuthorityNotFoundException.class)
    public @ResponseBody ErrorResponse handleAuthorityNotFoundException(AuthorityNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AuthorityAlreadyExistsException.class)
    public @ResponseBody ErrorResponse handleAuthorityAlreadyExistsException(AuthorityAlreadyExistsException e) {
        return ErrorResponse.create(e, HttpStatus.CONFLICT, e.getMessage());
    }

}
