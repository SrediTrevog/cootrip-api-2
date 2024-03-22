package ru.cootrip.api.echo.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.cootrip.api.echo.request.EchoRequest;
import ru.cootrip.api.echo.response.EchoResponse;

@RestController
@RequestMapping("/api/v1/echo")
public class EchoController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody EchoResponse echo(
            @RequestParam(name = "message") String message
    ) {
        return EchoResponse.create(message);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody EchoResponse echo(
            @RequestBody @Valid EchoRequest request
    ) {
        return EchoResponse.create(request.getMessage());
    }

}
