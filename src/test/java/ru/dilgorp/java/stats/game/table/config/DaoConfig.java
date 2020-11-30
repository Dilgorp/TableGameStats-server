package ru.dilgorp.java.stats.game.table.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.dao.MagicianDao;
import ru.dilgorp.java.stats.game.table.bom.dao.MurderEventDao;
import ru.dilgorp.java.stats.game.table.bom.dao.RoundDao;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.event.MurderEventEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.MagicianRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.MurderEventRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;

@Configuration
public class DaoConfig {

    @Autowired
    private MagicianRepository magicianRepository;

    @Autowired
    private Mapper<MagicianEntity, Magician> magicianMapper;

    @Autowired
    private MurderEventRepository murderEventRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private MapperWithOwner<MurderEventEntity, MurderEvent, RoundEntity> murderEventMapper;

    @Autowired
    private MapperWithOwner<RoundEntity, Round, GameEntity> roundMapper;

    @Autowired
    private Mapper<GameEntity, Game> gameMapper;

    @Autowired
    private GameRepository gameRepository;

    @Bean
    public MagicianDao magicianDao(){
        return new MagicianDao(magicianRepository, magicianMapper);
    }

    @Bean
    public MurderEventDao murderEventDao(){
        return new MurderEventDao(
                murderEventRepository,
                roundRepository,
                murderEventMapper,
                roundMapper,
                magicianDao()
        );
    }

    @Bean
    public RoundDao roundDao(){
        return new RoundDao(
                roundRepository,
                gameMapper,
                roundMapper,
                magicianDao(),
                murderEventDao()
        );
    }

    @Bean
    public GameDao gameDao(){
        return new GameDao(gameRepository, gameMapper, magicianDao(), roundDao());
    }
}
