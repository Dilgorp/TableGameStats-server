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
import ru.dilgorp.java.stats.game.table.bom.statistic.PlayerStatistic;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.domain.Player;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Класс для тестирования {@link PlayerStatisticController}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class,
        StatisticConfig.class
})
class PlayerStatisticControllerTest {

    @Autowired
    private RepositoryCleaner repositoryCleaner;

    @Autowired
    private GameGenerator gameGenerator;

    @Autowired
    private PlayerStatistic playerStatistic;

    @Autowired
    private GameDao gameDao;

    private PlayerStatisticController controller;

    @BeforeEach
    void setUp() {
        controller = new PlayerStatisticController(playerStatistic);
    }

    @AfterEach
    void tearDown() {
        repositoryCleaner.clean();
    }

    /**
     * Проверяем, что возвращается корректный ответ с общей статистикой по игрокам
     */
    @Test
    public void getCommonInfoIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        Response<List<StatisticRow>> givenResponse =
                new Response<>(SUCCESS, null, playerStatistic.commonInfo());

        // when
        Response<List<StatisticRow>> gettingResponse = controller.getCommonInfo();

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный ответ со статистикой по игре,
     * сгруппированной по игрокам
     */
    @Test
    public void getStatisticForGameIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));
        Game givenGame = games.get(0);

        Response<List<StatisticRow>> givenResponse = new Response<>(
                SUCCESS,
                null,
                playerStatistic.forGame(givenGame.getUuid())
        );

        // when
        Response<List<StatisticRow>> gettingResponse = controller.getStatisticForGame(givenGame.getUuid());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный ответ с ошибкой,
     * если статистика по игре, сгруппированная по игрокам, не найдена
     */
    @Test
    public void getStatisticForGameError() {
        // given
        UUID givenUuid = UUID.randomUUID();
        Response<List<StatisticRow>> givenResponse = new Response<>(
                ERROR,
                String.format(
                        "Не найдена статистика по игрокам в игре с идентификатором '%s'",
                        givenUuid.toString()
                ),
                new ArrayList<>()
        );

        // when
        Response<List<StatisticRow>> gettingResponse = controller.getStatisticForGame(givenUuid);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корретный ответ со статистикой раунда,
     * сгруппированной по игрокам
     */
    @Test
    public void getStatisticForRoundIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));
        Round givenRound = games.get(0).getRounds().get(0);

        Response<List<StatisticRow>> givenResponse = new Response<>(
                SUCCESS,
                null,
                playerStatistic.forRound(givenRound.getUuid())
        );

        // when
        Response<List<StatisticRow>> gettingResponse = controller.getStatisticForRound(givenRound.getUuid());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный ответ с ошибкой,
     * если статистика раунда, сгруппированная по игрокам, не найдена
     */
    @Test
    public void getStatisticForRoundError() {
        // given
        UUID givenUuid = UUID.randomUUID();
        Response<List<StatisticRow>> givenResponse = new Response<>(
                ERROR,
                String.format(
                        "Не найдена статистика по игрокам в раунде с идентификатором '%s'",
                        givenUuid.toString()
                ),
                new ArrayList<>()
        );

        // when
        Response<List<StatisticRow>> gettingResponse = controller.getStatisticForRound(givenUuid);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный ответ со статистикой по игроку
     */
    @Test
    public void getStatisticForPlayerIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));

        Player givenPlayer = games.get(0).getRounds().get(0)
                .getMurders().get(0).getInitiator().getPlayer();

        Response<StatisticRow> givenResponse = new Response<>(
                SUCCESS,
                null,
                playerStatistic.forPlayer(givenPlayer.getUuid())
        );

        // when
        Response<StatisticRow> gettingResponse = controller.getStatisticForPlayer(givenPlayer.getUuid());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный ответ с ошибкой,
     * если статистика по игроку не найдена
     */
    @Test
    public void getStatisticForPlayerError() {
        // given
        UUID givenUuid = UUID.randomUUID();
        Response<StatisticRow> givenResponse = new Response<>(
                ERROR,
                String.format(
                        "Не найдена статистика по игроку с идентификатором '%s'",
                        givenUuid.toString()
                ),
                new StatisticRow()
        );

        // when
        Response<StatisticRow> gettingResponse = controller.getStatisticForPlayer(givenUuid);

        // then
        assertEquals(givenResponse, gettingResponse);
    }
}