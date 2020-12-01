package ru.dilgorp.java.stats.game.table.bom.statistic;

import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;

import java.util.List;
import java.util.UUID;

/**
 * Формирует статистику по игрокам
 */
public interface PlayerStatistic {

    /**
     * Возвращает общую статистику по игрокам за все игры
     *
     * @return Список строк статистики по игрокам
     */
    List<StatisticRow> commonInfo();

    /**
     * Возвращает статистику по игре сгруппированную по игрокам
     *
     * @param gameUuid Идентификатор игры
     * @return Список строк статистики игры
     */
    List<StatisticRow> forGame(UUID gameUuid);

    /**
     * Возвращает статистику раунда сгруппированную по игрокам
     *
     * @param roundUuid Идентификатор раунда
     * @return Список строк статистики раунда
     */
    List<StatisticRow> forRound(UUID roundUuid);

    /**
     * Возвращает статистку игрока
     *
     * @param playerUuid идентификатор игрока
     * @return Статистика игрока
     */
    StatisticRow forPlayer(UUID playerUuid);
}
