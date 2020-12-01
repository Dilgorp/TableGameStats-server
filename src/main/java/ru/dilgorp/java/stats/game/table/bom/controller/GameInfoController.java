package ru.dilgorp.java.stats.game.table.bom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.List;
import java.util.UUID;

import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Контроллер, отвечающий за предоставление информации по играм
 */
@RestController
@RequestMapping("/info/battles_of_magicians")
@RequiredArgsConstructor
public class GameInfoController {

//    private final Dao<Game, UUID> gameDao;
//    private final StatisticManager statisticManager;
//
//    /**
//     * Возвращает список игр
//     *
//     * @return Ответ, содержащий список игр
//     */
//    @GetMapping
//    public Response<List<Game>> getGames() {
//        return new Response<>(SUCCESS, null, gameDao.findAll());
//    }
//
//    /**
//     * Возвращает сводную статистику по всем играм
//     *
//     * @return Ответ, содержащий статистику по всем играм
//     */
//    @GetMapping("/common")
//    public Response<StatisticRow> getCommonInfo() {
//        return new Response<>(SUCCESS, null, statisticManager.getCommonInfo());
//    }
//
//    /**
//     * Возвращает статистику по игре
//     *
//     * @param gameUuid идентификатор игры
//     * @return Ответ, содержащий статистику по игре
//     */
//    @GetMapping("/{uuid}")
//    public Response<List<StatisticRow>> getGameInfo(@PathVariable("uuid") UUID gameUuid) {
//        return new Response<>(SUCCESS, null, statisticManager.getGameInfo(gameUuid));
//    }
//
//    /**
//     * Возвращает статистику по раунду игры
//     *
//     * @param gameUuid  идентификатор игры
//     * @param roundUuid идентификатор раунда
//     * @return Ответ, содержащий статистику по раунду
//     */
//    @GetMapping("/{uuid}/round/{round_uuid}")
//    public Response<List<StatisticRow>> getRoundInfo(@PathVariable("uuid") UUID gameUuid, @PathVariable("round_uuid") UUID roundUuid) {
//        return new Response<>(SUCCESS, null, statisticManager.getRoundInfo(gameUuid, roundUuid));
//    }
//
//    /**
//     * Возвращает статистику по игроку в игре
//     *
//     * @param gameUuid   идентификатор игры
//     * @param playerUuid идентификатор игрока
//     * @return Ответ, содержащий статистику по игроку в игре
//     */
//    @GetMapping("/{uuid}/player/{player_uuid}")
//    public Response<List<StatisticRow>> getGameInfoByPlayer(@PathVariable("uuid") UUID gameUuid, @PathVariable("player_uuid") UUID playerUuid) {
//        return new Response<>(SUCCESS, null, statisticManager.getGameInfoByPlayer(gameUuid, playerUuid));
//    }
//
//    /**
//     * Возвращает статистику по игроку в раунде игры
//     *
//     * @param gameUuid   идентификатор игры
//     * @param roundUuid  идентификатор раунда
//     * @param playerUuid идентификатор игрока
//     * @return Ответ, содержащий статистику игроку в раунде игры
//     */
//    @GetMapping("/{id}/round/{round_id}/player/{player_uuid}")
//    public Response<List<StatisticRow>> getRoundInfoByPlayer(
//            @PathVariable("uuid") UUID gameUuid,
//            @PathVariable("round_uuid") UUID roundUuid,
//            @PathVariable("player_uuid") UUID playerUuid
//    ) {
//        return new Response<>(SUCCESS, null, statisticManager.getRoundInfoByPlayer(gameUuid, roundUuid, playerUuid));
//    }
//
//    /**
//     * Возвращает общую статистику игрока
//     *
//     * @param playerUuid идентификатор игрока
//     * @return Ответ, содержащий статистику игрока
//     */
//    @GetMapping("/common/{player_uuid}")
//    public Response<List<StatisticRow>> getPlayerInfo(@PathVariable("player_uuid") UUID playerUuid) {
//        return new Response<>(SUCCESS, null, statisticManager.getPlayerInfo(playerUuid));
//    }
}
