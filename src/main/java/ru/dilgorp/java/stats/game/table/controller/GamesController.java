package ru.dilgorp.java.stats.game.table.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.stats.game.table.domain.Game;
import ru.dilgorp.java.stats.game.table.response.Response;
import ru.dilgorp.java.stats.game.table.response.ResponseType;

import java.util.List;

/**
 * Контроллер для работы со списком игр
 */
@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GamesController {

    private final ObjectMapper objectMapper;

    /**
     * Возвращает список игр
     *
     * @return Ответ, содержащий список игр
     */
    @GetMapping
    @SneakyThrows
    public Response<List<Game>> getGames() {
        List<Game> list = objectMapper.readValue(
                new ClassPathResource("data/games.json").getInputStream().readAllBytes(),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Game.class)
        );

        return new Response<>(
                ResponseType.SUCCESS,
                null,
                list
        );
    }
}
