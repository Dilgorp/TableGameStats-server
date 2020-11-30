package ru.dilgorp.java.stats.game.table.bom.controller;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;
import ru.dilgorp.java.stats.game.table.response.Response;
import ru.dilgorp.java.stats.game.table.response.ResponseType;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;

/**
 * Класс для тестирования {@link GameEditController}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class
})
class GameEditControllerTest {

    @Autowired
    private Dao<Game, UUID> gameDao;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameGenerator gameGenerator;

    @Autowired
    private Mapper<GameEntity, Game> gameMapper;

    private GameEditController gameEditController;

    @BeforeEach
    void setUp() {
        gameEditController = new GameEditController(gameDao);
    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
    }

    @Test
    public void postIsCorrect(){
        // given
        Game givenGame = gameGenerator.generate(1).get(0);
        Response<Game> givenResponse = new Response<>(ResponseType.SUCCESS, null, givenGame);

        // when
        Response<Game> gettingResponse = gameEditController.post(givenGame);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    @Test
    public void putIsCorrect(){
        // given
        List<Game> givenGames = gameGenerator.generate(3);
        gameRepository.saveAll(gameMapper.modelsToEntities(givenGames));
        Game givenGame = givenGames.get(0);
        givenGame.setRounds(Lists.emptyList());
        Response<Game> givenResponse = new Response<>(ResponseType.SUCCESS, null, givenGame);

        // when
        Response<Game> gettingResponse = gameEditController.put(givenGame);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    @Test
    public void deleteIsCorrect(){
        // given
        List<Game> givenGames = gameGenerator.generate(3);
        gameRepository.saveAll(gameMapper.modelsToEntities(givenGames));
        Game givenGame = givenGames.get(0);
        Response<Game> givenResponse = new Response<>(ResponseType.SUCCESS, null, null);

        // when
        Response<Game> gettingResponse = gameEditController.delete(givenGame.getUuid());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    @Test
    public void deleteError(){
        // given
        UUID givenUuid = UUID.randomUUID();
        Response<Game> givenResponse = new Response<>(
                ERROR,
                String.format("Игра по идентификатору '%s' не найдена", givenUuid),
                null
        );

        // when
        Response<Game> gettingResponse = gameEditController.delete(givenUuid);

        // then
        assertEquals(givenResponse, gettingResponse);
    }
}