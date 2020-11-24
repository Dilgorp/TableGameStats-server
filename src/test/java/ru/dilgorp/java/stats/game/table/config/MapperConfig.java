package ru.dilgorp.java.stats.game.table.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.mapper.MagicianMapper;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;

@Configuration
public class MapperConfig {

    @Bean
    public Mapper<MagicianEntity, Magician> magicianMapper(){
        return new MagicianMapper();
    }
}
