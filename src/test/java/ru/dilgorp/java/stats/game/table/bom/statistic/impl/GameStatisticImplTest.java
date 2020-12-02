package ru.dilgorp.java.stats.game.table.bom.statistic.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.bom.statistic.GameStatistic;
import ru.dilgorp.java.stats.game.table.bom.statistic.StatisticManager;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.domain.Player;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс для тестирования {@link GameStatistic}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class
})
class GameStatisticImplTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private StatisticManager statisticManager;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GameGenerator gameGenerator;

    private GameStatistic gameStatistic;

    @BeforeEach
    void setUp() {
        gameStatistic = new GameStatisticImpl(statisticManager, gameDao);
    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
    }

    /**
     * Проверяем, что общая статистика возвращается корректно
     */
    @Test
    public void getCommonInfoIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        StatisticRow givenStatistic = new StatisticRow();
        Set<Player> players = new HashSet<>();
        games.forEach(game -> {
            List<Round> rounds = game.getRounds();
            givenStatistic.setRounds(givenStatistic.getRounds() + rounds.size());
            givenStatistic.setGames(givenStatistic.getGames() + 1);
            statisticManager.addRoundStatistic(givenStatistic, players, rounds);
        });
        givenStatistic.setPlayers(players.size());

        // when
        StatisticRow gettingStatistic = gameStatistic.getCommonInfo();

        // then
        assertEquals(givenStatistic, gettingStatistic);
    }

    /**
     * Проверяем, что статистика по играм возвращается корректно
     */
    @Test
    public void byGamesIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        Set<Player> players = new HashSet<>();
        List<StatisticRow> givenStatistic = new ArrayList<>();
        games.forEach(game -> {
            StatisticRow statisticRow = new StatisticRow();

            List<Round> rounds = game.getRounds();
            statisticManager.addRoundStatistic(statisticRow, players, rounds);

            statisticRow.setPlayers(players.size());
            statisticRow.setGames(1);
            statisticRow.setRounds(rounds.size());
            statisticRow.setGameUuid(game.getUuid());

            givenStatistic.add(statisticRow);
            players.clear();
        });

        // when
        List<StatisticRow> gettingStatistic = gameStatistic.byGames();

        // then
        assertEquals(givenStatistic, gettingStatistic);
    }

    /**
     * Проверяем, что статистика по игре возвращается корректно
     */
    @Test
    public void gameInfoIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        Game givenGame = games.get(0);

        Set<Player> players = new HashSet<>();
        StatisticRow givenRow = new StatisticRow();
        List<Round> rounds = givenGame.getRounds();
        statisticManager.addRoundStatistic(givenRow, players, rounds);

        givenRow.setPlayers(players.size());
        givenRow.setGames(1);
        givenRow.setRounds(rounds.size());
        givenRow.setGameUuid(givenGame.getUuid());

        // when
        StatisticRow gettingRow = gameStatistic.gameInfo(givenGame.getUuid());
        StatisticRow emptyRow = gameStatistic.gameInfo(UUID.randomUUID());

        // then
        assertEquals(givenRow, gettingRow);
        assertEquals(new StatisticRow(), emptyRow);
    }
}