package ru.dilgorp.java.stats.game.table.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import ru.dilgorp.java.stats.game.table.domain.Game;
import ru.dilgorp.java.stats.game.table.response.Response;
import ru.dilgorp.java.stats.game.table.response.ResponseType;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс для тестирования {@link GamesController}
 */
class GamesControllerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private GamesController gamesController;

    @BeforeEach
    void setUp() {
        gamesController = new GamesController(objectMapper);
    }

    @Test
    public void getGamesIsCorrect() throws IOException {
        // given
        List<Game> list = objectMapper.readValue(
                new ClassPathResource("data/games.json").getInputStream().readAllBytes(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Game.class)
        );

        Response<List<Game>> givenResponse = new Response<>(
                ResponseType.SUCCESS,
                null,
                list
        );

        // when
        Response<List<Game>> gettingResponse = gamesController.getGames();

        // then
        assertEquals(givenResponse, gettingResponse);
    }
}