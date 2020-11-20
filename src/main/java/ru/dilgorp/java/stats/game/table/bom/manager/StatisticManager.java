package ru.dilgorp.java.stats.game.table.bom.manager;

import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;

import java.util.List;

/**
 * Менеджер, отвечающий за формирование статистики
 */
public interface StatisticManager {

    /**
     * Возвращает общую статистику
     *
     * @return список строк общей статистики
     */
    List<StatisticRow> getCommonInfo();

    /**
     * Возвращает статистику игры
     *
     * @param gameId идентификатор игры
     * @return список строк статистики игры
     */
    List<StatisticRow> getGameInfo(String gameId);

    /**
     * Возвращает статистику раунда игры
     *
     * @param gameId  идентификатор игры
     * @param roundId идентификатор раунда
     * @return список строк статистики раунда игры
     */
    List<StatisticRow> getRoundInfo(String gameId, String roundId);

    /**
     * Возвращает статистку мага в игре
     *
     * @param gameId     идентификатор игры
     * @param magicianId идентификатор мага
     * @return список строк статистики мага в игре
     */
    List<StatisticRow> getGameInfoByMagician(String gameId, String magicianId);

    /**
     * Возвращает статистику мага в раунде игры
     *
     * @param gameId     идентификатор игры
     * @param roundId    идентификатор раунда
     * @param magicianId идентификатор мага
     * @return список строк статистики мага в раунде игры
     */
    List<StatisticRow> getRoundInfoByMagician(String gameId, String roundId, String magicianId);

    /**
     * Возвращает общую статистику игрока
     *
     * @param playerId идентификатор игрока
     * @return спискок строк статистики игрока
     */
    List<StatisticRow> getPlayerInfo(String playerId);
}
