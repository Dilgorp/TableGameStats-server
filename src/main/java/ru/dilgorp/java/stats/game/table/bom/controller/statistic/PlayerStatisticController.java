package ru.dilgorp.java.stats.game.table.bom.controller.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.statistic.PlayerStatistic;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.List;
import java.util.UUID;

import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Контроллер, отвечающий за предоставление статистики по игрокам
 */
@RestController
@RequestMapping("/info/battles_of_magicians/statistic/players")
@RequiredArgsConstructor
public class PlayerStatisticController {

    private final PlayerStatistic playerStatistic;

    @GetMapping
    public Response<List<StatisticRow>> getCommonInfo() {
        return new Response<>(SUCCESS, null, playerStatistic.commonInfo());
    }

    /**
     * Возвращает статистику игры, сгруппированную по игрокам
     *
     * @param gameUuid Идентификатор игры
     * @return Ответ, содержащий статистику игры, сгруппированную по игрокам
     */
    @GetMapping("/for_game/{game_uuid}")
    public Response<List<StatisticRow>> getStatisticForGame(@PathVariable("game_uuid") UUID gameUuid) {
        List<StatisticRow> statistic = playerStatistic.forGame(gameUuid);
        if (statistic.size() == 0) {
            return new Response<>(
                    ERROR,
                    String.format(
                            "Не найдена статистика по игрокам в игре с идентификатором '%s'",
                            gameUuid.toString()
                    ),
                    statistic
            );
        }

        return new Response<>(SUCCESS, null, statistic);
    }

    /**
     * Возвращает статистику раунда, сгруппированную по игрокам
     *
     * @param roundUuid Идентификатор раунда
     * @return Ответ, содержащий статистику раунда, сгруппированную по игрокам
     */
    @GetMapping("/for_round/{round_uuid}")
    public Response<List<StatisticRow>> getStatisticForRound(@PathVariable("round_uuid") UUID roundUuid) {
        List<StatisticRow> statistic = playerStatistic.forRound(roundUuid);
        if (statistic.size() == 0) {
            return new Response<>(
                    ERROR,
                    String.format(
                            "Не найдена статистика по игрокам в раунде с идентификатором '%s'",
                            roundUuid.toString()
                    ),
                    statistic
            );
        }

        return new Response<>(SUCCESS, null, statistic);
    }

    /**
     * Возвращает общую статистику игрока
     *
     * @param uuid Идентификатор игрока
     * @return Ответ, содержащий общую статистику игрока
     */
    @GetMapping("/{uuid}")
    public Response<StatisticRow> getStatisticForPlayer(@PathVariable("uuid") UUID uuid) {
        StatisticRow statistic = playerStatistic.forPlayer(uuid);
        if (statistic.isEmpty()) {
            return new Response<>(
                    ERROR,
                    String.format(
                            "Не найдена статистика по игроку с идентификатором '%s'",
                            uuid.toString()
                    ),
                    statistic
            );
        }

        return new Response<>(SUCCESS, null, statistic);
    }
}
