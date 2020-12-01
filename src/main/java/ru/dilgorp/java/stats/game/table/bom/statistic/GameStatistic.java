package ru.dilgorp.java.stats.game.table.bom.statistic;

import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;

import java.util.List;
import java.util.UUID;

/**
 * Формирует статистику по играм
 */
public interface GameStatistic {

    /**
     * Возвращает общую статистику
     *
     * @return общая статистика
     */
    StatisticRow getCommonInfo();

    /**
     * Возвращает общую статистику сгруппированную по играм
     *
     * @return список строк статистики по играм
     */
    List<StatisticRow> byGames();

    /**
     * Возвращает статистику игры
     *
     * @param gameUuid идентификатор игры
     * @return статистика игры
     */
    StatisticRow gameInfo(UUID gameUuid);
}
