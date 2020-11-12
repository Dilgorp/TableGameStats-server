package ru.dilgorp.java.stats.game.table.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.dilgorp.java.stats.game.table.domain.AppUser;
import ru.dilgorp.java.stats.game.table.domain.auth.Authority;
import ru.dilgorp.java.stats.game.table.domain.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, отвечающий за генерацию элементов для тестов
 */
public class UserGenerator {
    private final BCryptPasswordEncoder encoder;
    private final Authority[] authorities = Authority.values();

    @Autowired
    public UserGenerator(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Генерирует список пользователей для тестов
     *
     * @param count количество пользователей в списке
     * @return сгенерированный список
     */
    public List<AppUser> generate(int count) {
        List<AppUser> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            AppUser user = new AppUser();
            user.setId(Integer.toString(i));
            user.setUsername(Integer.toString(i));
            user.setPassword(encoder.encode(Integer.toString(i)));
            user.setAuthorities(List.of(authorities[i % authorities.length]));
            users.add(user);
        }

        return users;
    }

    public List<UserDTO> generateDTO(int count) {
        List<UserDTO> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            UserDTO user = new UserDTO();
            user.setId(Integer.toString(i));
            user.setUsername(Integer.toString(i));
            user.setPassword(encoder.encode(Integer.toString(i)));
            user.setAuthorities(List.of(authorities[i % authorities.length]));
            users.add(user);
        }

        return users;
    }

    public UserDTO copy(UserDTO userDTO){
        UserDTO copy = new UserDTO();
        copy.setId(userDTO.getId());
        copy.setUsername(userDTO.getUsername());
        copy.setPassword(userDTO.getUsername());
        copy.setAuthorities(userDTO.getAuthorities());
        return copy;
    }
}
