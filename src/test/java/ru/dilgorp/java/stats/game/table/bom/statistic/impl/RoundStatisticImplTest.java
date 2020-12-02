package ru.dilgorp.java.stats.game.table.bom.statistic.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.dao.RoundDao;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;
import ru.dilgorp.java.stats.game.table.bom.statistic.RoundStatistic;
import ru.dilgorp.java.stats.game.table.bom.statistic.StatisticManager;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.domain.Player;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс для тестирования {@link RoundStatisticImpl}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class
})
class RoundStatisticImplTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private StatisticManager statisticManager;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private RoundDao roundDao;

    @Autowired
    private GameGenerator gameGenerator;

    private RoundStatistic roundStatistic;

    @BeforeEach
    void setUp() {
        roundStatistic = new RoundStatisticImpl(statisticManager, gameDao, roundDao);
    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
        roundRepository.deleteAll();
    }

    /**
     * Проверяем, что статистика игры по раундам возвращается корректно
     */
    @Test
    public void byRoundsIsCorrect() {
        // given
        Game givenGame = gameGenerator.generate(1).get(0);
        gameDao.save(givenGame);

        List<StatisticRow> givenStatistic = new ArrayList<>();
        Set<Player> players = new HashSet<>();

        givenGame.getRounds().forEach(round -> {
            StatisticRow statisticRow = new StatisticRow();
            statisticRow.setGameUuid(givenGame.getUuid());

            statisticManager.addRoundStatistic(statisticRow, players, List.of(round));
            statisticRow.setRoundUuid(round.getUuid());
            statisticRow.setRounds(1);
            statisticRow.setGames(1);
            statisticRow.setPlayers(players.size());
            givenStatistic.add(statisticRow);

            players.clear();
        });

        // when
        List<StatisticRow> gettingStatistic = roundStatistic.byRounds(givenGame.getUuid());
        List<StatisticRow> emptyList = roundStatistic.byRounds(UUID.randomUUID());

        // then
        assertEquals(givenStatistic, gettingStatistic);
        assertEquals(new ArrayList<StatisticRow>(), emptyList);
    }

    /**
     * Проверяем, что статистика по раунду в игре возвращается корректно
     */
    @Test
    public void roundInfoIsCorrect() {
        Game givenGame = gameGenerator.generate(1).get(0);
        gameDao.save(givenGame);

        Round givenRound = givenGame.getRounds().get(0);
        StatisticRow givenRow = new StatisticRow();
        Set<Player> players = new HashSet<>();

        statisticManager.addRoundStatistic(givenRow, players, List.of(givenRound));
        givenRow.setRoundUuid(givenRound.getUuid());
        givenRow.setRounds(1);
        givenRow.setGames(1);
        givenRow.setPlayers(players.size());

        // when
        StatisticRow gettingRow = roundStatistic.roundInfo(givenRound.getUuid());
        StatisticRow emptyRow = roundStatistic.roundInfo(UUID.randomUUID());

        assertEquals(givenRow, gettingRow);
        assertEquals(new StatisticRow(), emptyRow);
    }
}