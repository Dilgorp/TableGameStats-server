package ru.dilgorp.java.stats.game.table.bom.controller.statistic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.RepositoryCleaner;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.statistic.RoundStatistic;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Класс для тестирования {@link RoundStatisticController}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class,
        StatisticConfig.class
})
class RoundStatisticControllerTest {

    @Autowired
    private RepositoryCleaner repositoryCleaner;

    @Autowired
    private RoundStatistic roundStatistic;

    @Autowired
    private GameGenerator gameGenerator;

    @Autowired
    private GameDao gameDao;

    private RoundStatisticController roundStatisticController;

    @BeforeEach
    void setUp() {
        roundStatisticController = new RoundStatisticController(roundStatistic);
    }

    @AfterEach
    void tearDown() {
        repositoryCleaner.clean();
    }

    /**
     * Проверяем, что возвращается корректный ответ со статистикой игры по раундам
     */
    @Test
    public void getStatisticByRoundsIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        Game givenGame = games.get(0);
        Response<List<StatisticRow>> givenResponse = new Response<>(
                SUCCESS, null, roundStatistic.byRounds(givenGame.getUuid())
        );

        // when
        Response<List<StatisticRow>> gettingResponse =
                roundStatisticController.getStatisticByRounds(givenGame.getUuid());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, возвращается корректный ответ с ошибкой,
     * если статистика по раундам в игре не найдена
     */
    @Test
    public void getStatisticByRoundsError() {
        // given
        UUID givenUuid = UUID.randomUUID();
        Response<List<StatisticRow>> givenResponse = new Response<>(
                ERROR,
                String.format(
                        "Не найдена статистика по раундам в игре с идентификатором '%s'",
                        givenUuid.toString()
                ),
                new ArrayList<>()
        );

        // when
        Response<List<StatisticRow>> gettingStatistic
                = roundStatisticController.getStatisticByRounds(givenUuid);

        // then
        assertEquals(givenResponse, gettingStatistic);
    }

    /**
     * Проверяем, что возвращается корректный ответ со статистикой по раунду
     */
    @Test
    public void getRoundInfoIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));
        Round givenRound = games.get(0).getRounds().get(0);

        Response<StatisticRow> givenResponse = new Response<>(
                SUCCESS, null, roundStatistic.roundInfo(givenRound.getUuid())
        );

        // when
        Response<StatisticRow> gettingResponse = roundStatisticController.getRoundInfo(givenRound.getUuid());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный ответ с ошибкой,
     * если статистика по раунду не найдена
     */
    @Test
    public void getRoundInfoError() {
        // given
        UUID givenUuid = UUID.randomUUID();
        Response<StatisticRow> givenResponse = new Response<>(
                ERROR,
                String.format(
                        "Не найдена статистика по раунду с идентификатором '%s'",
                        givenUuid.toString()
                ),
                new StatisticRow()
        );

        // when
        Response<StatisticRow> gettingResponse = roundStatisticController.getRoundInfo(givenUuid);

        // then
        assertEquals(givenResponse, gettingResponse);
    }
}