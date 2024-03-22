package ru.cootrip.api.common.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.cootrip.api.common.jwt.component.JWTComponent;
import ru.cootrip.api.common.security.service.CustomUserDetailsService;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTComponent jwtComponent;

    private final CustomUserDetailsService customUserDetailsService;

    public JWTAuthenticationFilter(JWTComponent jwtComponent, CustomUserDetailsService customUserDetailsService) {
        this.jwtComponent = jwtComponent;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String accessToken;

        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            accessToken = authorizationHeader.substring(7);
        } else {
            accessToken = null;
        }

        UUID deviceId;
        try {
            deviceId = jwtComponent.getAccessTokenDeviceId(Objects.requireNonNull(accessToken));
        } catch (Exception ignored) {
            deviceId = null;
        }

        if (deviceId != null) {
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(deviceId.toString());

            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            final WebAuthenticationDetails details = new WebAuthenticationDetailsSource().buildDetails(request);
            token.setDetails(details);

            SecurityContextHolder.getContext().setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }

}
