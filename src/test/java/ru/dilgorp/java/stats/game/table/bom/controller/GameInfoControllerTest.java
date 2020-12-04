package ru.dilgorp.java.stats.game.table.bom.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.RepositoryCleaner;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Класс для тестирования {@link GameInfoController}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class
})
class GameInfoControllerTest {

    @Autowired
    private RepositoryCleaner repositoryCleaner;

    @Autowired
    private GameDao gameDao;

    @Autowired
    private GameGenerator gameGenerator;

    private GameInfoController gameInfoController;

    @BeforeEach
    void setUp() {
        gameInfoController = new GameInfoController(gameDao);
    }

    @AfterEach
    void tearDown() {
        repositoryCleaner.clean();
    }

    /**
     * Проверяем, что возвращается корректный ответ со списком игр
     */
    @Test
    public void getGamesIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));
        Response<List<Game>> givenResponse = new Response<>(SUCCESS, null, games);

        // when
        Response<List<Game>> gettingResponse = gameInfoController.getGames();

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный ответ с игрой по идентификатору
     */
    @Test
    public void getGameIsCorrect() {
        // given
        List<Game> games = gameGenerator.generate(2);
        games.forEach(game -> gameDao.save(game));
        Game givenGame = games.get(0);
        Response<Game> givenResponse = new Response<>(SUCCESS, null, givenGame);

        // when
        Response<Game> gettingResponse = gameInfoController.getGame(givenGame.getUuid());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный ответ с указанием ошибки,
     * когда игра по идентификатору не найдена
     */
    @Test
    public void getGameError() {
        // given
        UUID givenUuid = UUID.randomUUID();
        Response<Game> givenResponse = new Response<>(
                ERROR,
                String.format("Игра по идентификатору '%s' не найдена.", givenUuid.toString()),
                null
        );

        // when
        Response<Game> gettingResponse = gameInfoController.getGame(givenUuid);

        // then
        assertEquals(givenResponse, gettingResponse);
    }
}