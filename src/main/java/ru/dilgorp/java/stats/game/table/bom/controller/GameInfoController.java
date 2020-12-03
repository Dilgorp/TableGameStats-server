package ru.dilgorp.java.stats.game.table.bom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.List;
import java.util.UUID;

import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Контроллер, отвечающий за предоставление информации по играм
 */
@RestController
@RequestMapping("/info/battles_of_magicians")
@RequiredArgsConstructor
public class GameInfoController {

    private final Dao<Game, UUID> gameDao;

    /**
     * Возвращает список игр
     *
     * @return Ответ, содержащий список игр
     */
    @GetMapping
    public Response<List<Game>> getGames() {
        return new Response<>(SUCCESS, null, gameDao.findAll());
    }

    @GetMapping("/{uuid}")
    public Response<Game> getGame(@PathVariable("uuid") UUID uuid) {
        return gameDao.findById(uuid).map(game -> new Response<>(SUCCESS, null, game))
                .orElseGet(() ->
                        new Response<>(
                                ERROR,
                                String.format("Игра по идентификатору '%s' не найдена.", uuid.toString()),
                                null)
                );
    }
}
