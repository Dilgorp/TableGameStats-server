package ru.dilgorp.java.stats.game.table.bom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.GameEvent;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.domain.Player;
import ru.dilgorp.java.stats.game.table.repository.PlayerRepository;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.*;

import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

@RestController
@RequestMapping("/edit/battles_of_magicians")
@RequiredArgsConstructor
public class GameEditController {
    private final Dao<Game, UUID> gameDao;
    private final PlayerRepository playerRepository;

    @GetMapping("/mock")
    public Response<List<Game>> fillMockData(){

        List<Game> all = gameDao.findAll();

        return new Response<>(
                SUCCESS,
                null,
                all
        );
    }

    /**
     * Добавляет игру в базу
     *
     * @param game игра, которую нужно добавить
     * @return Ответ, содержащий данные по добавленной игре
     */
    @PostMapping
    public Response<Game> postGame(@RequestBody Game game) {
        return new Response<>(SUCCESS, null, gameDao.save(game));
    }

    /**
     * Обновляет игру в базе
     *
     * @param game игры, которую нужно обновить
     * @return Ответ, содержащий данные по обновленной игре
     */
    @PutMapping
    public Response<Game> putGame(@RequestBody Game game) {
        return new Response<>(SUCCESS, null, gameDao.save(game));
    }

    /**
     * Удаляет игру
     *
     * @param uuid иденфтикатор игры
     * @return Ответ, содержащий результат оперции удаления
     * SUCCESS - успешное удаление, ERROR - ошибка при удаление
     */
    @DeleteMapping("/{uuid}")
    public Response<Game> deleteGame(@PathVariable UUID uuid) {
        Optional<Game> byId = gameDao.findById(uuid);
        if (byId.isEmpty()) {
            return new Response<>(
                    ERROR,
                    String.format("Игра по идентификатору '%s' не найдена", uuid),
                    null
            );
        }

        gameDao.delete(uuid);
        return new Response<>(SUCCESS, null, null);
    }
}
