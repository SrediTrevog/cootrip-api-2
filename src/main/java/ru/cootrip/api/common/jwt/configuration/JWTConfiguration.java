package ru.cootrip.api.common.jwt.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration("jwt-configuration")
@ConfigurationProperties("jwt")
@EnableConfigurationProperties(JWTConfiguration.class)
public class JWTConfiguration {

    @Autowired
    private AccessTokenConfiguration accessTokenConfiguration;

    @Autowired
    private RefreshTokenConfiguration refreshTokenConfiguration;

    private String issuer;

}
