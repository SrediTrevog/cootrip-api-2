package ru.cootrip.api.echo.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
public class EchoRequest {

    @JsonProperty
    private String message;

}
