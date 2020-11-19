package ru.dilgorp.java.stats.game.table.bom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.stats.game.table.bom.domain.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.Round;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.MagicianRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;
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
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final RoundRepository roundRepository;
    private final MagicianRepository magicianRepository;

    @GetMapping("/mock")
    public Response<List<Game>> fillMockData(){

        return new Response<>(
                SUCCESS,
                null,
                null
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
        return new Response<>(SUCCESS, null, gameRepository.save(game));
    }

    /**
     * Обновляет игру в базе
     *
     * @param game игры, которую нужно обновить
     * @return Ответ, содержащий данные по обновленной игре
     */
    @PutMapping
    public Response<Game> putGame(@RequestBody Game game) {
        return new Response<>(SUCCESS, null, gameRepository.save(game));
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
        Optional<Game> byId = gameRepository.findById(uuid);
        if (byId.isEmpty()) {
            return new Response<>(
                    ERROR,
                    String.format("Игра по идентификатору '%s' не найдена", uuid),
                    null
            );
        }

        gameRepository.delete(byId.get());
        return new Response<>(SUCCESS, null, null);
    }
}
