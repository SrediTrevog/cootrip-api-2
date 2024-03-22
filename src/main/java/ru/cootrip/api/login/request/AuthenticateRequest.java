package ru.cootrip.api.login.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;
import ru.cootrip.api.device.entity.Device;

import java.util.UUID;

@Getter
@Validated
public class AuthenticateRequest {

    @NotNull
    @JsonProperty
    private UUID otpId;

    @NotNull
    @Size(min = 6, max = 6)
    @Pattern(regexp = "^[A-z0-9]{6}$")
    @JsonProperty
    private String password;

    @NotNull
    @Size(min = 1, max = 32)
    @Pattern(regexp = "^[A-zА-яЁё0-9 _\\-\".,:()]{1,32}$")
    @JsonProperty
    private String deviceName;

    @NotNull
    @Size(min = 1, max = 64)
    @Pattern(regexp = "^[A-zА-яЁё0-9 _\\-\".,:()]{1,64}$")
    @JsonProperty
    private String deviceInfo;

    @NotNull
    private Device.Platform devicePlatform;

}
