package ru.dilgorp.java.stats.game.table.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dilgorp.java.stats.game.table.domain.Player;
import ru.dilgorp.java.stats.game.table.repository.PlayerRepository;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

@RestController
@RequestMapping("/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerRepository playerRepository;

    /**
     * Возвращает список пользователей
     *
     * @return Ответ, содержащий список пользователей
     */
    @GetMapping
    public Response<List<Player>> getPlayers() {
        return new Response<>(
                SUCCESS,
                null,
                playerRepository.findAll()
        );
    }

    /**
     * Добавляет нового игрока.
     * Если игрок с указанным именем сущестует, возвращает ошибку
     *
     * @param player новый игрок
     * @return ответ, содержащий данные об игроке,
     * SUCCESS - в случае успешного выполнения, ERROR - в случае ошибки
     */
    @PostMapping
    public Response<Player> postPlayer(@RequestBody final Player player) {
        Player playerDB = playerRepository.findByName(player.getName());
        if (playerDB != null) {
            return new Response<>(
                    ERROR,
                    String.format("В базе уже имеется игрок с именем '%s'", player.getName()),
                    playerDB
            );
        }

        return new Response<>(SUCCESS, null, playerRepository.save(player));
    }

    /**
     * Возвращает игрока по идентификатору.
     * Если игрок не найден, возвращает ошибку
     *
     * @param uuid идентификатор пользователя
     * @return ответ, содержащий данные об игроке,
     * SUCCESS - в случае успешного выполнения, ERROR - в случае ошибки
     */
    @GetMapping("/{id}")
    public Response<Player> getPlayer(@PathVariable("id") final UUID uuid) {
        Optional<Player> byId = playerRepository.findById(uuid);
        return byId.map(player -> new Response<>(SUCCESS, null, player))
                .orElseGet(() -> new Response<>(
                        ERROR,
                        String.format("Игрок с идентификатором '%s' не найден", uuid),
                        null
                ));
    }

    /**
     * Обновляет данные об игроке
     * Если игрок не найден, возвращает ошибку.
     *
     * @param player игрок, данные которого нужно обновить
     * @return ответ, содержащий данные об игроке,
     * SUCCESS - в случае успешного выполнения, ERROR - в случае ошибки
     */
    @PutMapping
    public Response<Player> putPlayer(@RequestBody final Player player) {
        Optional<Player> byId = playerRepository.findById(player.getUuid());
        return byId.map(playerDB -> new Response<>(SUCCESS, null, playerRepository.save(player)))
                .orElseGet(() -> new Response<>(
                        ERROR,
                        String.format("Игрок с идентификатором '%s' не найден", player.getUuid()),
                        null
                ));
    }

    /**
     * Удаляет игрока по идентификатору.
     * Если игрок не найден. Возвращает ошибку
     *
     * @param uuid идентификатор игрока
     * @return ответ, содержащий данные об игроке,
     * SUCCESS - в случае успешного выполнения, ERROR - в случае ошибки
     */
    @DeleteMapping("/{uuid}")
    public Response<Player> deleteUser(@PathVariable("uuid") UUID uuid) {
        Optional<Player> byId = playerRepository.findById(uuid);

        Response<Player> response;
        if (byId.isPresent()) {
            playerRepository.delete(byId.get());
            response = new Response<>(SUCCESS, null, null);
        } else {
            response = new Response<>(
                    ERROR,
                    String.format("Игрок с идентификатором '%s' не найден", uuid),
                    null
            );
        }

        return response;
    }
}
