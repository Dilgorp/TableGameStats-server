package ru.dilgorp.java.stats.game.table.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepository;
import ru.dilgorp.java.stats.game.table.repository.AppUserRepositoryTestImpl;

@Configuration
public class RepositoriesConfig {

    @Bean
    public AppUserRepository appuserRepository(){
        return new AppUserRepositoryTestImpl();
    }
}
