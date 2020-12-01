package ru.dilgorp.java.stats.game.table.bom.statistic;

import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;

import java.util.List;
import java.util.UUID;

/**
 * Формирует статистику по раундам
 */
public interface RoundStatistic {

    /**
     * Возвращает статистику по раундам игры
     *
     * @param gameUuid Идентификатор игры
     * @return Список строк статистики по раундам
     */
    List<StatisticRow> byRounds(UUID gameUuid);

    /**
     * Возвращает статистику раунда игры
     *
     * @param roundUuid Идентификатор раунда
     * @return список строк статистики раунда игры
     */
    StatisticRow roundInfo(UUID roundUuid);
}
