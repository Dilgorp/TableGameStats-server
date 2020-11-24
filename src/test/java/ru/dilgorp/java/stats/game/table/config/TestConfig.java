package ru.dilgorp.java.stats.game.table.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.dilgorp.java.stats.game.table.generator.MagicianGenerator;
import ru.dilgorp.java.stats.game.table.generator.PlayerGenerator;
import ru.dilgorp.java.stats.game.table.generator.UserGenerator;

@Configuration
public class TestConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
