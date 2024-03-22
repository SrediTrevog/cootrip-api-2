package ru.cootrip.api.login.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
public class RefreshTokenRequest {

    @NotNull
    @Size(min = 86)
    @Pattern(regexp = "^(?:[A-Za-z0-9_\\-]+\\.){2}[A-Za-z0-9_\\-]+$")
    @JsonProperty
    private String refreshToken;

}
