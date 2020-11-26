package ru.dilgorp.java.stats.game.table.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.dilgorp.java.stats.game.table.generator.*;

@Configuration()
public class GeneratorsConfig {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public UserGenerator userGenerator() {
        return new UserGenerator(bCryptPasswordEncoder);
    }

    @Bean
    public PlayerGenerator playerGenerator(){
        return new PlayerGenerator();
    }

    @Bean
    public MagicianGenerator magicianGenerator(){
        return new MagicianGenerator(playerGenerator());
    }

    @Bean
    public MurderEventGenerator murderEventGenerator(){
        return new MurderEventGenerator(magicianGenerator());
    }

    @Bean
    public RoundGenerator roundGenerator(){
        return new RoundGenerator(murderEventGenerator());
    }

    @Bean
    public GameGenerator gameGenerator(){
        return new GameGenerator(roundGenerator());
    }
}
