package ru.dilgorp.java.stats.game.table.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.dao.RoundDao;
import ru.dilgorp.java.stats.game.table.bom.statistic.GameStatistic;
import ru.dilgorp.java.stats.game.table.bom.statistic.PlayerStatistic;
import ru.dilgorp.java.stats.game.table.bom.statistic.RoundStatistic;
import ru.dilgorp.java.stats.game.table.bom.statistic.StatisticManager;
import ru.dilgorp.java.stats.game.table.bom.statistic.impl.GameStatisticImpl;
import ru.dilgorp.java.stats.game.table.bom.statistic.impl.PlayerStatisticImpl;
import ru.dilgorp.java.stats.game.table.bom.statistic.impl.RoundStatisticImpl;
import ru.dilgorp.java.stats.game.table.bom.statistic.impl.StatisticManagerImpl;

@Configuration
public class StatisticConfig {

    @Autowired
    private GameDao gameDao;

    @Autowired
    private RoundDao roundDao;

    @Bean
    public StatisticManager statisticManager() {
        return new StatisticManagerImpl();
    }

    @Bean
    public GameStatistic gameStatistic() {
        return new GameStatisticImpl(statisticManager(), gameDao);
    }

    @Bean
    public RoundStatistic roundStatistic() {
        return new RoundStatisticImpl(
                statisticManager(),
                gameDao,
                roundDao
        );
    }

    @Bean
    public PlayerStatistic playerStatistic(){
        return new PlayerStatisticImpl(
                gameDao,
                roundDao
        );
    }
}
