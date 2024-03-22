package ru.cootrip.api.device.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.cootrip.api.device.exception.DeviceNotFoundException;

@ControllerAdvice
public class DeviceExceptionHandler {

    @ExceptionHandler(DeviceNotFoundException.class)
    public @ResponseBody ErrorResponse handleDeviceNotFoundException(DeviceNotFoundException e) {
        return ErrorResponse.create(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

}
