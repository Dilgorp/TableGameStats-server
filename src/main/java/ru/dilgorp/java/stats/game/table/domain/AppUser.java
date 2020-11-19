package ru.dilgorp.java.stats.game.table.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.java.stats.game.table.domain.auth.Authority;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

/**
 * Представляет пользователя.
 * Содержит доступные права.
 */
@Data
@Entity(name = "appUsers")
@NoArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String username;
    private String password;

    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_uuid"))
    @Enumerated(EnumType.STRING)
    private List<Authority> authorities;
}
