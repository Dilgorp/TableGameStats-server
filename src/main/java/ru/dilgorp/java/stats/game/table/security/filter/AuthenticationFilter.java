package ru.dilgorp.java.stats.game.table.security.filter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.dilgorp.java.stats.game.table.domain.AppUser;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 * Фильтр для аутентификации
 */
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final String headerString;
    private final String secret;
    private final String tokenPrefix;

    @Autowired
    public AuthenticationFilter(
            AuthenticationManager authenticationManager,
            String headerString,
            String secret,
            String tokenPrefix
    ) {
        this.authenticationManager = authenticationManager;
        this.headerString = headerString;
        this.secret = secret;
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AppUser appUser = new ObjectMapper().readValue(
                request.getInputStream(),
                AppUser.class
        );

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        appUser.getUsername(),
                        appUser.getPassword()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .sign(HMAC512(secret.getBytes()));
        response.addHeader(headerString, tokenPrefix + token);
    }
}
