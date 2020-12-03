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
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;
import ru.dilgorp.java.stats.game.table.bom.statistic.PlayerStatistic;
import ru.dilgorp.java.stats.game.table.bom.statistic.StatisticManager;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.domain.Player;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс для тестирования {@link PlayerStatisticImpl}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class
})
class PlayerStatisticImplTest {

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

    private PlayerStatistic playerStatistic;

    @BeforeEach
    void setUp() {
        playerStatistic = new PlayerStatisticImpl(gameDao, roundDao);
    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
        roundRepository.deleteAll();
    }

    /**
     * Проверяем, что общая статистика возвращается корректно
     */
    @Test
    public void commonInfoIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));
        List<StatisticRow> givenStatistic = getStatisticResult(getCommonStatistic());

        // when
        List<StatisticRow> gettingStatistic = playerStatistic.commonInfo();

        // then
        assertEquals(givenStatistic, gettingStatistic);
    }

    /**
     * Проверяем, что статистика по игре возвращается корректно
     */
    @Test
    public void forGameIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        Game givenGame = games.get(0);
        Map<Player, StatisticRow> statistic = new HashMap<>();

        givenGame.getRounds().forEach(round -> round.getMurders()
                .forEach(murderEvent -> addEventStatistic(statistic, murderEvent)));

        List<StatisticRow> givenStatistic = getStatisticResult(statistic);

        // when
        List<StatisticRow> gettingStatistic = playerStatistic.forGame(givenGame.getUuid());
        List<StatisticRow> emptyStatistic = playerStatistic.forGame(UUID.randomUUID());

        // then
        assertEquals(givenStatistic, gettingStatistic);
        assertEquals(new ArrayList<StatisticRow>(), emptyStatistic);
    }

    /**
     * Проверяем, что статистика по раунду возращается корректно
     */
    @Test
    public void forRoundIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        Round givenRound = games.get(0).getRounds().get(0);
        Map<Player, StatisticRow> statistic = new HashMap<>();

        givenRound.getMurders()
                .forEach(murderEvent -> addEventStatistic(statistic, murderEvent));
        List<StatisticRow> givenStatistic = getStatisticResult(statistic);

        // when
        List<StatisticRow> gettingStatistic = playerStatistic.forRound(givenRound.getUuid());
        List<StatisticRow> emptyStatistic = playerStatistic.forGame(UUID.randomUUID());

        // then
        assertEquals(givenStatistic, gettingStatistic);
        assertEquals(new ArrayList<StatisticRow>(), emptyStatistic);
    }

    /**
     * Проверяем, что для игрока статистика возвращается корректно
     */
    @Test
    public void forPlayerIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        Player givenPlayer = games.get(0).getRounds().get(0)
                .getMurders().get(0).getInitiator().getPlayer();

        Map<Player, StatisticRow> commonStatistic = getCommonStatistic();
        StatisticRow givenStatistic = null;
        for (Map.Entry<Player, StatisticRow> entry : commonStatistic.entrySet()) {
            if (entry.getKey().getUuid().equals(givenPlayer.getUuid())) {
                givenStatistic = entry.getValue();
                break;
            }
        }

        // when
        StatisticRow gettingStatistic = playerStatistic.forPlayer(givenPlayer.getUuid());
        StatisticRow emptyStatistic = playerStatistic.forPlayer(UUID.randomUUID());

        // then
        assertEquals(givenStatistic, gettingStatistic);
        assertEquals(new StatisticRow(), emptyStatistic);
    }

    private Map<Player, StatisticRow> getCommonStatistic() {
        Map<Player, StatisticRow> statistic = new HashMap<>();

        List<Game> games = gameDao.findAll();
        games.forEach(game -> game.getRounds()
                .forEach(round -> round.getMurders()
                        .forEach(murderEvent -> addEventStatistic(statistic, murderEvent))));
        return statistic;
    }

    private void addEventStatistic(Map<Player, StatisticRow> statistic, MurderEvent murderEvent) {
        Player initiator = murderEvent.getInitiator().getPlayer();
        StatisticRow initiatorRow = getStatisticRow(statistic, initiator);

        Player target = murderEvent.getTarget().getPlayer();
        StatisticRow targetRow = getStatisticRow(statistic, target);

        int quantity = murderEvent.getQuantity();
        initiatorRow.setKills(initiatorRow.getKills() + quantity);
        targetRow.setDeaths(targetRow.getDeaths() + quantity);

        statistic.put(initiator, initiatorRow);
        statistic.put(target, targetRow);
    }

    private StatisticRow getStatisticRow(Map<Player, StatisticRow> statistic, Player player) {
        StatisticRow statisticRow = statistic.get(player);
        if (statisticRow == null) {
            statisticRow = new StatisticRow();
            statisticRow.setPlayer(player);
        }
        return statisticRow;
    }

    private List<StatisticRow> getStatisticResult(Map<Player, StatisticRow> statistic) {
        List<StatisticRow> result = new ArrayList<>(statistic.values());
        result.sort((o1, o2) -> o2.getKills() - o1.getKills());
        return result;
    }
}