package ru.cootrip.api.echo.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EchoResponse {

    @JsonProperty
    private String message;

    private EchoResponse(String message) {
        this.message = message;
    }

    public static EchoResponse create(String message) {
        return new EchoResponse(message);
    }

}
