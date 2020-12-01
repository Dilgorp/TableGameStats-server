package ru.dilgorp.java.stats.game.table.bom.statistic;

import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.List;
import java.util.Set;

/**
 * Формирует строки статистики
 */
public interface StatisticManager {

    /**
     * Добавляет в строку статистики информацию по убийстам и смертям в раунде.
     * Также добавляет игроков раунда в множество.
     *
     * @param statisticRow Строка статистики
     * @param players      Множество игроков
     * @param rounds       Список раундов
     */
    void addRoundStatistic(StatisticRow statisticRow, Set<Player> players, List<Round> rounds);
}
