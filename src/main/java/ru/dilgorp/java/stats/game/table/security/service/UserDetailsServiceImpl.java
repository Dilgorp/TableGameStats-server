package ru.dilgorp.java.stats.game.table.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.domain.AppUser;
import ru.dilgorp.java.stats.game.table.domain.auth.Authority;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Сервис получения данных о пользователе
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null){
            throw new UsernameNotFoundException(String.format("User with username '%s' not found", username));
        }
        return new User(appUser.getUsername(), appUser.getPassword(), mapToGrantedAuthorities(appUser.getAuthorities()));
    }

    /**
     * Преобразует коллекцию {@link Authority} в коллекцию {@link GrantedAuthority}
     * @param authorities права доступа
     * @return системные права доступа
     */
    private Collection<? extends GrantedAuthority> mapToGrantedAuthorities(Collection<Authority> authorities){
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.name()))
                .collect(Collectors.toList());
    }
}
