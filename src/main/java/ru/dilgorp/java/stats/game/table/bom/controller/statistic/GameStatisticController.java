package ru.dilgorp.java.stats.game.table.bom.controller.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.statistic.GameStatistic;
import ru.dilgorp.java.stats.game.table.response.Response;

import java.util.List;
import java.util.UUID;

import static ru.dilgorp.java.stats.game.table.response.ResponseType.ERROR;
import static ru.dilgorp.java.stats.game.table.response.ResponseType.SUCCESS;

/**
 * Контроллер, отвечающий за предоставление статистики по играм
 */
@RestController
@RequestMapping("/info/battles_of_magicians/statistic/games")
@RequiredArgsConstructor
public class GameStatisticController {

    private final GameStatistic gameStatistic;

    /**
     * Возвращает общую статистику
     *
     * @return Ответ, содержащий общую статистику
     */
    @GetMapping
    public Response<StatisticRow> getCommonInfo() {
        return new Response<>(SUCCESS, null, gameStatistic.getCommonInfo());
    }

    /**
     * Возвращает статистику сгруппированную по играм
     *
     * @return Ответ, содержащий статистику сгруппированную по играм
     */
    @GetMapping("/by_games")
    public Response<List<StatisticRow>> getStatisticByGames() {
        return new Response<>(SUCCESS, null, gameStatistic.byGames());
    }

    /**
     * Возвращает статистику по игре
     *
     * @param uuid Идентификатор игры
     * @return Ответ, содержащий статистику по игре
     * SUCCESS - статистика найдена, ERROR - статистика по игре не найдена
     */
    @GetMapping("/{uuid}")
    public Response<StatisticRow> getGameInfo(@PathVariable("uuid") UUID uuid) {
        StatisticRow statisticRow = gameStatistic.gameInfo(uuid);
        if (statisticRow.isEmpty()) {
            return new Response<>(
                    ERROR,
                    String.format("Не найдена статистика по игре с идентификатором '%s'", uuid.toString()),
                    statisticRow
            );
        }

        return new Response<>(SUCCESS, null, statisticRow);
    }
}
