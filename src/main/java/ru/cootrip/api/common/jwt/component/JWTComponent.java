package ru.cootrip.api.common.jwt.component;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.cootrip.api.common.jwt.configuration.JWTConfiguration;
import ru.cootrip.api.common.jwt.entity.AccessToken;
import ru.cootrip.api.common.jwt.entity.RefreshToken;
import ru.cootrip.api.common.jwt.enumeration.TokenType;
import ru.cootrip.api.common.jwt.exception.InvalidAccessTokenException;
import ru.cootrip.api.common.jwt.exception.InvalidRefreshTokenException;
import ru.cootrip.api.device.entity.Device;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Component
public class JWTComponent {

    private final JWTConfiguration jwtConfiguration;

    public JWTComponent(
            @Qualifier("jwt-configuration") JWTConfiguration jwtConfiguration
    ) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public AccessToken generateAccessToken(Device device) {
        final Map<String, Object> claims = Collections.singletonMap(TokenType.getClaimKey(), TokenType.ACCESS.name());

        final Date issuedAt = new Date();
        final Date expiration = new Date(issuedAt.getTime() + jwtConfiguration.getAccessTokenConfiguration().getLifetimeInMillis());

        final String accessToken = Jwts.builder()
                .setSubject(device.getId().toString())
                .setIssuer(jwtConfiguration.getIssuer())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .addClaims(claims)
                .signWith(jwtConfiguration.getAccessTokenConfiguration().getSecretKey())
                .compact();

        return AccessToken.create(accessToken, expiration.toInstant());
    }

    public RefreshToken generateRefreshToken(Device device) {
        final Map<String, Object> claims = Collections.singletonMap(TokenType.getClaimKey(), TokenType.REFRESH.name());

        final Date issuedAt = new Date();

        final String refreshToken = Jwts.builder()
                .setSubject(device.getId().toString())
                .setIssuer(jwtConfiguration.getIssuer())
                .setIssuedAt(issuedAt)
                .addClaims(claims)
                .signWith(jwtConfiguration.getRefreshTokenConfiguration().getSecretKey())
                .compact();

        return RefreshToken.create(refreshToken);
    }

    public UUID getAccessTokenDeviceId(String token) throws InvalidAccessTokenException {
        final Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(jwtConfiguration.getAccessTokenConfiguration().getSecretKey())
                .build()
                .parseClaimsJws(token);

        try {
            final Claims claims = jws.getBody();
            final TokenType tokenType = TokenType.valueOf(claims.get(TokenType.getClaimKey()).toString());

            if (tokenType != TokenType.ACCESS) {
                throw new RuntimeException();
            }

            if (claims.getExpiration().before(new Date())) {
                throw new RuntimeException();
            }

            return UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            throwInvalidAccessTokenException("Invalid access token");
        }

        return null;
    }

    public UUID getRefreshTokenDeviceId(String token) throws InvalidRefreshTokenException {
        final Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(jwtConfiguration.getRefreshTokenConfiguration().getSecretKey())
                .build()
                .parseClaimsJws(token);

        try {
            final Claims claims = jws.getBody();
            final TokenType tokenType = TokenType.valueOf(claims.get(TokenType.getClaimKey()).toString());

            if (tokenType != TokenType.REFRESH) {
                throw new RuntimeException();
            }

            return UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            throwInvalidRefreshTokenException("Invalid refresh token");
        }

        return null;
    }

    private InvalidAccessTokenException getInvalidAccessTokenException(String template, Object... args) {
        final String message = String.format(template, args);
        return InvalidAccessTokenException.create(message);
    }

    private void throwInvalidAccessTokenException(String template, Object... args) throws InvalidAccessTokenException {
        throw getInvalidAccessTokenException(template, args);
    }

    private InvalidRefreshTokenException getInvalidRefreshTokenException(String template, Object... args) {
        final String message = String.format(template, args);
        return InvalidRefreshTokenException.create(message);
    }

    private void throwInvalidRefreshTokenException(
            String template,
            Object... args
    ) throws InvalidRefreshTokenException {
        throw getInvalidRefreshTokenException(template, args);
    }

}
