package ru.dilgorp.java.stats.game.table.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.dilgorp.java.stats.game.table.domain.auth.Authority;

import java.util.List;

/**
 * Представляет пользователя.
 * Содержит доступные права.
 */
@Data
@Document("appUsers")
@NoArgsConstructor
public class AppUser {

    @Id
    private String id;

    private String username;
    private String password;
    private List<Authority> authorities;

    public AppUser(String username, String password, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }
}
