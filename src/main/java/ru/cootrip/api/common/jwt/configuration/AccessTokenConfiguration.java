package ru.cootrip.api.common.jwt.configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt.access-token")
@EnableConfigurationProperties(AccessTokenConfiguration.class)
public class AccessTokenConfiguration {

    private String secretKey;

    private long lifetimeInMillis;

    public SecretKey getSecretKey() {
        final byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

}
