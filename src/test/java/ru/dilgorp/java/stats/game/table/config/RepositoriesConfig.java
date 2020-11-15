package ru.dilgorp.java.stats.game.table.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepositoryTestImpl;
import ru.dilgorp.java.stats.game.table.repository.PlayerRepository;
import ru.dilgorp.java.stats.game.table.repository.PlayerRepositoryTestImpl;

@Configuration
public class RepositoriesConfig {

    @Bean
    public AppUserRepository appuserRepository(){
        return new AppUserRepositoryTestImpl();
    }

    @Bean
    public PlayerRepository playerRepository(){
        return new PlayerRepositoryTestImpl();
    }
}
