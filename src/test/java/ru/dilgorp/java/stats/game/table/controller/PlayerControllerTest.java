package ru.dilgorp.java.stats.game.table.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.config.RepositoriesConfig;
import ru.dilgorp.java.stats.game.table.config.TestConfig;
import ru.dilgorp.java.stats.game.table.domain.Player;
import ru.dilgorp.java.stats.game.table.generator.PlayerGenerator;
import ru.dilgorp.java.stats.game.table.repository.PlayerRepository;
import ru.dilgorp.java.stats.game.table.response.Response;
import ru.dilgorp.java.stats.game.table.response.ResponseType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Класс для тестирования {@link PlayerController}
 */
@SpringJUnitConfig({RepositoriesConfig.class, TestConfig.class})
class PlayerControllerTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerGenerator playerGenerator;

    private PlayerController playerController;

    @BeforeEach
    void setUp(){
        playerController = new PlayerController(playerRepository);
    }

    @AfterEach
    void tearDown(){
        playerRepository.deleteAll();
    }

    /**
     * Проверяем, что возвращается корректный список игроков
     */
    @Test
    public void getPlayersIsCorrect(){
        // given
        List<Player> givenPlayers = playerGenerator.generate(3);
        playerRepository.saveAll(givenPlayers);
        Response<List<Player>> givenResponse = new Response<>(
                SUCCESS,
                null,
                givenPlayers
        );

        // when
        Response<List<Player>> gettingResponse = playerController.getPlayers();

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что игрок добавляется корректно
     */
    @Test
    public void postPlayerIsCorrect(){
        // given
        Player player = playerGenerator.generate(1).get(0);
        Response<Player> givenResponse = new Response<>(
                SUCCESS, null, playerGenerator.copyOf(player)
        );

        // when
        Response<Player> gettingResponse = playerController.postPlayer(playerGenerator.copyOf(player));

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что при попытке добавить пользователя с именем, которое есть в базу, возникает ошибка
     */
    @Test
    public void postPlayerError(){
        // given
        Player player = playerGenerator.generate(1).get(0);
        playerRepository.save(playerGenerator.copyOf(player));
        Response<Player> givenResponse = new Response<>(
                ERROR,
                String.format("В базе уже имеется игрок с именем '%s'", player.getName()),
                playerRepository.findByName(player.getName())
        );

        // when
        Response<Player> gettingResponse = playerController.postPlayer(playerGenerator.copyOf(player));

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что возвращается корректный игрок
     */
    @Test
    public void getPlayerIsCorrect(){
        // given
        Player player = playerGenerator.generate(1).get(0);
        playerRepository.save(playerGenerator.copyOf(player));
        Response<Player> givenResponse = new Response<>(
                SUCCESS,
                null,
                playerGenerator.copyOf(player)
        );

        // when
        Response<Player> gettingResponse = playerController.getPlayer(player.getId());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что при попытке получить несуществующего игрока, возвращается ошибка
     */
    @Test
    public void getPlayerError(){
        // given
        String id = "id";
        Response<Player> givenResponse = new Response<>(
                ERROR,
                String.format("Игрок с идентификатором '%s' не найден", id),
                null
        );

        // when
        Response<Player> gettingResponse = playerController.getPlayer(id);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что данные игрока обновляются корректно
     */
    @Test
    public void putPlayerIsCorrect(){
        // given
        Player player = playerGenerator.generate(1).get(0);
        playerRepository.save(playerGenerator.copyOf(player));
        player.setName("new name");
        Response<Player> givenResponse = new Response<>(SUCCESS, null, player);

        // when
        Response<Player> gettingResponse = playerController.putPlayer(playerGenerator.copyOf(player));

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что при попытке обновить данные несуществующего игрока, возникает ошибка
     */
    @Test
    public void putPlayerError(){
        // given
        Player player = playerGenerator.generate(1).get(0);
        Response<Player> givenResponse = new Response<>(
                ERROR,
                String.format("Игрок с идентификатором '%s' не найден", player.getId()),
                null
        );

        // when
        Response<Player> gettingResponse = playerController.putPlayer(player);

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем корректность удаления игрока
     */
    @Test
    public void deletePlayerIsCorrect(){
        // given
        Player player = playerGenerator.generate(1).get(0);
        playerRepository.save(player);
        Response<Player> givenResponse = new Response<>(SUCCESS, null, null);

        // when
        Response<Player> gettingResponse = playerController.deleteUser(player.getId());

        // then
        assertEquals(givenResponse, gettingResponse);
    }

    /**
     * Проверяем, что при попытке удалить несуществующего игрока, возникает ошибка
     */
    @Test
    public void deletePlayerError(){
        // given
        String id = "id";
        Response<Player> givenResponse = new Response<>(
                ERROR,
                String.format("Игрок с идентификатором '%s' не найден", id),
                null
        );

        // when
        Response<Player> gettingResponse = playerController.deleteUser(id);

        // then
        assertEquals(givenResponse, gettingResponse);
    }
}