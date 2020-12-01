package ru.dilgorp.java.stats.game.table.bom.manager;

import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;

import java.util.List;
import java.util.UUID;

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
     * @param gameUuid идентификатор игры
     * @return список строк статистики игры
     */
    List<StatisticRow> getGameInfo(UUID gameUuid);

    /**
     * Возвращает статистику раунда игры
     *
     * @param gameUuid  идентификатор игры
     * @param roundUuid идентификатор раунда
     * @return список строк статистики раунда игры
     */
    List<StatisticRow> getRoundInfo(UUID gameUuid, UUID roundUuid);

    /**
     * Возвращает статистку игрока в игре
     *
     * @param gameUuid   идентификатор игры
     * @param playerUuid идентификатор игрока
     * @return список строк статистики игрока в игре
     */
    List<StatisticRow> getGameInfoByPlayer(UUID gameUuid, UUID playerUuid);

    /**
     * Возвращает статистику игрока в раунде игры
     *
     * @param gameUuid   идентификатор игры
     * @param roundUuid  идентификатор раунда
     * @param playerUuid идентификатор игрока
     * @return список строк статистики игрока в раунде игры
     */
    List<StatisticRow> getRoundInfoByPlayer(UUID gameUuid, UUID roundUuid, UUID playerUuid);

    /**
     * Возвращает общую статистику игрока
     *
     * @param playerUuid идентификатор игрока
     * @return спискок строк статистики игрока
     */
    List<StatisticRow> getPlayerInfo(UUID playerUuid);
}
