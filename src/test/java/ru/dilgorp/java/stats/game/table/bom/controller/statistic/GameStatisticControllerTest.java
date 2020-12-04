package ru.dilgorp.java.stats.game.table.bom.controller.statistic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.RepositoryCleaner;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.statistic.GameStatistic;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Класс для тестирования {@link GameStatisticController}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class,
        StatisticConfig.class
})
class GameStatisticControllerTest {

    @Autowired
    private RepositoryCleaner repositoryCleaner;

    @Autowired
    private GameStatistic gameStatistic;

    @Autowired
    private GameGenerator gameGenerator;

    @Autowired
    private GameDao gameDao;

    private GameStatisticController gameStatisticController;

    @BeforeEach
    void setUp() {
        gameStatisticController = new GameStatisticController(gameStatistic);
    }

    @AfterEach
    void tearDown() {
        repositoryCleaner.clean();
    }

    /**
     * Проверяем, что возвращается корректный ответ с общей статистикой
     */
    @Test
    public void getCommonInfoIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        Response<StatisticRow> givenResponse = new Response<>(
                SUCCESS,
                null,
                gameStatistic.getCommonInfo()
        );

        // when
        Response<StatisticRow> gettingResponse = gameStatisticController.getCommonInfo();

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный ответ со статистикой,
     * сгруппированной по играм
     */
    @Test
    public void getStatisticByGamesIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        Response<List<StatisticRow>> givenResponse = new Response<>(
                SUCCESS,
                null,
                gameStatistic.byGames()
        );

        // when
        Response<List<StatisticRow>> gettingResponse = gameStatisticController.getStatisticByGames();

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается ответ с корректной статистикой по игре
     */
    @Test
    public void getGameInfoIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));
        Game givenGame = games.get(0);

        Response<StatisticRow> givenResponse = new Response<>(
                SUCCESS, null, gameStatistic.gameInfo(givenGame.getUuid())
        );

        // when
        Response<StatisticRow> gettingResponse = gameStatisticController.getGameInfo(givenGame.getUuid());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный ответ с ошибкой,
     * если не найдена статистика по игре
     */
    @Test
    public void getGameInfoError() {
        // given
        UUID givenUuid = UUID.randomUUID();
        Response<StatisticRow> givenResponse = new Response<>(
                ERROR,
                String.format("Не найдена статистика по игре с идентификатором '%s'", givenUuid.toString()),
                new StatisticRow()
        );

        // when
        Response<StatisticRow> gettingResponse = gameStatisticController.getGameInfo(givenUuid);

        // then
        assertEquals(gettingResponse, gettingResponse);
    }
}