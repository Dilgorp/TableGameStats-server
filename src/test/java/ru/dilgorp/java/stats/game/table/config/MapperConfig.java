package ru.dilgorp.java.stats.game.table.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.event.MurderEventEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.mapper.GameMapper;
import ru.dilgorp.java.stats.game.table.bom.domain.mapper.MagicianMapper;
import ru.dilgorp.java.stats.game.table.bom.domain.mapper.MurderEventMapper;
import ru.dilgorp.java.stats.game.table.bom.domain.mapper.RoundMapper;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;

@Configuration
public class MapperConfig {

    @Bean
    public Mapper<MagicianEntity, Magician> magicianMapper(){
        return new MagicianMapper();
    }

    @Bean
    public MapperWithOwner<MurderEventEntity, MurderEvent, RoundEntity> murderEventMapper(){
        return new MurderEventMapper(magicianMapper());
    }

    @Bean
    public MapperWithOwner<RoundEntity, Round, GameEntity> roundMapper(){
        return new RoundMapper(murderEventMapper(), magicianMapper());
    }

    @Bean
    public Mapper<GameEntity, Game> gameMapper(){
        return new GameMapper(roundMapper(), magicianMapper());
    }
}
