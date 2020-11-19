package ru.dilgorp.java.stats.game.table.bom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.stats.game.table.bom.domain.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.manager.StatisticManager;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.List;

import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Контроллер, отвечающий за предоставление информации по играм
 */
@RestController
@RequestMapping("/info/battles_of_magicians")
@RequiredArgsConstructor
public class GameInfoController {

    private final GameRepository gameRepository;
    private final StatisticManager statisticManager;

    /**
     * Возвращает список игр
     *
     * @return Ответ, содержащий список игр
     */
    @GetMapping
    public Response<List<Game>> getGames() {
        return new Response<>(SUCCESS, null, gameRepository.findAll());
    }

    /**
     * Возвращает сводную статистику по всем играм
     *
     * @return Ответ, содержащий статистику по всем играм
     */
    @GetMapping("/common")
    public Response<List<StatisticRow>> getCommonInfo() {
        return new Response<>(SUCCESS, null, statisticManager.getCommonInfo());
    }

    /**
     * Возвращает статистику по игре
     *
     * @param gameId идентификатор игры
     * @return Ответ, содержащий статистику по игре
     */
    @GetMapping("/{id}")
    public Response<List<StatisticRow>> getGameInfo(@PathVariable("id") String gameId) {
        return new Response<>(SUCCESS, null, statisticManager.getGameInfo(gameId));
    }

    /**
     * Возвращает статистику по раунду игры
     *
     * @param gameId  идентификатор игры
     * @param roundId идентификатор раунда
     * @return Ответ, содержащий статистику по раунду
     */
    @GetMapping("/{id}/round/{round_id}")
    public Response<List<StatisticRow>> getRoundInfo(@PathVariable("id") String gameId, @PathVariable("round_id") String roundId) {
        return new Response<>(SUCCESS, null, statisticManager.getRoundInfo(gameId, roundId));
    }

    /**
     * Возвращает статистику по магу в игре
     *
     * @param gameId     идентификатор игры
     * @param magicianId идентификатор мага
     * @return Ответ, содержащий статистику по магу в игре
     */
    @GetMapping("/{id}/magician/{magician_id}")
    public Response<List<StatisticRow>> getGameInfoByMagician(@PathVariable("id") String gameId, @PathVariable("magician_id") String magicianId) {
        return new Response<>(SUCCESS, null, statisticManager.getGameInfoByMagician(gameId, magicianId));
    }

    /**
     * Возвращает статистику по могу в раунде игры
     *
     * @param gameId     идентификатор игры
     * @param roundId    идентификатор раунда
     * @param magicianId идентификатор мага
     * @return Ответ, содержащий статистику мага в раунде игры
     */
    @GetMapping("/{id}/round/{round_id}/magician/{magician_id}")
    public Response<List<StatisticRow>> getRoundInfoByMagician(
            @PathVariable("id") String gameId,
            @PathVariable("round_id") String roundId,
            @PathVariable("magician_id") String magicianId
    ) {
        return new Response<>(SUCCESS, null, statisticManager.getRoundInfoByMagician(gameId, roundId, magicianId));
    }

    /**
     * Возвращает общую статистику игрока
     *
     * @param playerId идентификатор игрока
     * @return Ответ, содержащий статистику игрока
     */
    @GetMapping("/common/{player_id}")
    public Response<List<StatisticRow>> getPlayerInfo(@PathVariable("magician_id") String playerId) {
        return new Response<>(SUCCESS, null, statisticManager.getPlayerInfo(playerId));
    }
}
