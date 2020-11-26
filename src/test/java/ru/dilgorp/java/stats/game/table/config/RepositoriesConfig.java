package ru.dilgorp.java.stats.game.table.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dilgorp.java.stats.game.table.bom.repository.MagicianRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.MurderEventRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;
import ru.dilgorp.java.stats.game.table.repository.*;

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

    @Bean
    public MagicianRepository magicianRepository(){
        return new MagicianRepositoryTestImpl();
    }

    @Bean
    public MurderEventRepository murderEventRepository(){
        return new MurderEventRepositoryTestImpl();
    }

    @Bean
    public RoundRepository roundRepository(){
        return new RoundRepositoryTestImpl();
    }
}