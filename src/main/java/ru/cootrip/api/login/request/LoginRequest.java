package ru.cootrip.api.login.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
public class LoginRequest {

    @NotNull
    @Size(min = 7, max = 15)
    @Pattern(regexp = "^\\+\\d{1,3}\\d{3}\\d{3}\\d{4}$")
    @JsonProperty
    private String phone;

}
