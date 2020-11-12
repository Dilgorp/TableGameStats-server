package ru.dilgorp.java.stats.game.table.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.java.stats.game.table.domain.auth.Authority;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private List<Authority> authorities;
}
