package ru.dilgorp.java.stats.game.table.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Фильтр для авторизации
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final String headerString;
    private final String secret;
    private final String tokenPrefix;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthorizationFilter(
            AuthenticationManager authenticationManager,
            String headerString,
            String secret,
            String tokenPrefix,
            UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.headerString = headerString;
        this.secret = secret;
        this.tokenPrefix = tokenPrefix;
        this.userDetailsService = userDetailsService;
    }

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        String header = request.getHeader(headerString);
        if (header == null || !header.startsWith(tokenPrefix)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                getAuthenticationToken(request);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * Возвращает токен авторизации
     * @param request запрос
     * @return токен, содержащий имя пользователя, пароль, и права доступа
     */
    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(headerString);
        if (token == null) {
            return null;
        }

        String user = JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build()
                .verify(token.replace(tokenPrefix, ""))
                .getSubject();

        if (user == null) {
            return null;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user);

        return new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities()
        );
    }
}
