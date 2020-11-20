package ru.dilgorp.java.stats.game.table.bom.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;

import java.util.List;

/**
 * Реализация менеджера, отвечающего за формирование статистики
 */
@Service
@RequiredArgsConstructor
public class StatisticManagerImpl implements StatisticManager {

    private final GameRepository gameRepository;

    /**
     * Возвращает общую статистику
     *
     * @return список строк общей статистики
     */
    @Override
    public List<StatisticRow> getCommonInfo() {
        return null;
    }

    /**
     * Возвращает статистику игры
     *
     * @param gameId идентификатор игры
     * @return список строк статистики игры
     */
    @Override
    public List<StatisticRow> getGameInfo(String gameId) {
        return null;
    }

    /**
     * Возвращает статистику раунда игры
     *
     * @param gameId  идентификатор игры
     * @param roundId идентификатор раунда
     * @return список строк статистики раунда игры
     */
    @Override
    public List<StatisticRow> getRoundInfo(String gameId, String roundId) {
        return null;
    }

    /**
     * Возвращает статистку мага в игре
     *
     * @param gameId     идентификатор игры
     * @param magicianId идентификатор мага
     * @return список строк статистики мага в игре
     */
    @Override
    public List<StatisticRow> getGameInfoByMagician(String gameId, String magicianId) {
        return null;
    }

    /**
     * Возвращает статистику мага в раунде игры
     *
     * @param gameId     идентификатор игры
     * @param roundId    идентификатор раунда
     * @param magicianId идентификатор мага
     * @return список строк статистики мага в раунде игры
     */
    @Override
    public List<StatisticRow> getRoundInfoByMagician(String gameId, String roundId, String magicianId) {
        return null;
    }

    /**
     * Возвращает общую статистику игрока
     *
     * @param playerId идентификатор игрока
     * @return спискок строк статистики игрока
     */
    @Override
    public List<StatisticRow> getPlayerInfo(String playerId) {
        return null;
    }
}
