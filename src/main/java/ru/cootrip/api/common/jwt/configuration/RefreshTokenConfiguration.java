package ru.cootrip.api.common.jwt.configuration;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.refresh-token")
@EnableConfigurationProperties(RefreshTokenConfiguration.class)
public class RefreshTokenConfiguration {

    private String secretKey;

    public SecretKey getSecretKey() {
        final byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

}
