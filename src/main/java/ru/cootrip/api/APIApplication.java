package ru.cootrip.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.cootrip.api.common.jwt.configuration.AccessTokenConfiguration;
import ru.cootrip.api.common.jwt.configuration.JWTConfiguration;
import ru.cootrip.api.common.jwt.configuration.RefreshTokenConfiguration;

@SpringBootApplication
public class APIApplication {

    public static void main(String[] args) {
        SpringApplication.run(APIApplication.class, args);
    }

}
