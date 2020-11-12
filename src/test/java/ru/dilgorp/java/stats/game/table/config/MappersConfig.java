package ru.dilgorp.java.stats.game.table.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.dilgorp.java.stats.game.table.domain.AppUser;
import ru.dilgorp.java.stats.game.table.domain.dto.UserDTO;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.domain.mapper.UserMapper;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;

@Configuration
public class MappersConfig {

    @Bean
    public Mapper<AppUser, UserDTO> appUserUserDTOMapper(AppUserRepository appUserRepository, BCryptPasswordEncoder encoder) {
        return new UserMapper(appUserRepository, encoder);
    }
}
