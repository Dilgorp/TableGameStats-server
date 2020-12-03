package ru.dilgorp.java.stats.game.table.bom.controller.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.statistic.RoundStatistic;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.List;
import java.util.UUID;

import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Контроллер, отвечающий за предоставление статистики по раундам
 */
@RestController
@RequestMapping("/info/battles_of_magicians/statistic/rounds")
@RequiredArgsConstructor
public class RoundStatisticController {

    private final RoundStatistic roundStatistic;

    /**
     * Возвращает статистику по игре, сгруппированную по раундом
     *
     * @param gameUuid Идентификатор игры
     * @return Ответ, содержащий статистику игры, сгруппированную по раундам
     */
    @GetMapping("/by_rounds/{game_uuid}")
    public Response<List<StatisticRow>> getStatisticByRounds(@PathVariable("game_uuid") UUID gameUuid) {
        List<StatisticRow> statistic = roundStatistic.byRounds(gameUuid);
        if (statistic.size() == 0) {
            return new Response<>(
                    ERROR,
                    String.format(
                            "Не найдена статистика по раундам в игре с идентификатором '%s'",
                            gameUuid.toString()
                    ),
                    statistic
            );
        }

        return new Response<>(SUCCESS, null, statistic);
    }

    /**
     * Возвращает статистику раунда
     *
     * @param uuid Идентификатор раунда
     * @return Ответ, содержащий статистику раунда
     */
    @GetMapping("/{uuid}")
    public Response<StatisticRow> getRoundInfo(@PathVariable("uuid") UUID uuid) {
        StatisticRow statistic = roundStatistic.roundInfo(uuid);
        if (statistic.isEmpty()) {
            return new Response<>(
                    ERROR,
                    String.format(
                            "Не найдена статистика по раунду с идентификатором '%s'",
                            uuid.toString()
                    ),
                    statistic
            );
        }

        return new Response<>(SUCCESS, null, statistic);
    }
}
