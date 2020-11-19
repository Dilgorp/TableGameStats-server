package ru.dilgorp.java.stats.game.table.security.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.dilgorp.java.stats.game.table.security.filter.AuthenticationFilter;
import ru.dilgorp.java.stats.game.table.security.filter.AuthorizationFilter;

import static ru.dilgorp.java.stats.game.table.domain.auth.Authority.*;

/**
 * Конфигурация авторизации
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.header-string}")
    private String headerString;

    @Value("${security.secret}")
    private String secret;

    @Value("${security.token-prefix}")
    private String tokenPrefix;

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http){
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/users").hasAnyAuthority(WRITE_USER.name(), ALL.name())
                .antMatchers("/players").hasAnyAuthority(WRITE_PLAYER_INFO.name(), ALL.name())
                .antMatchers("/info").hasAnyAuthority(READ_GAME_INFO.name(), ALL.name())
                .antMatchers("/edit").hasAnyAuthority(WRITE_GAME_INFO.name(), ALL.name())
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthenticationFilter(authenticationManager(), headerString, secret, tokenPrefix))
                .addFilter(new AuthorizationFilter(authenticationManager(), headerString, secret, tokenPrefix, userDetailsService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @SneakyThrows
    protected void configure(AuthenticationManagerBuilder auth){
       auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
