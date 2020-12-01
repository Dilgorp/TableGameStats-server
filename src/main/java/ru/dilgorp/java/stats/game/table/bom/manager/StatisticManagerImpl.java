package ru.dilgorp.java.stats.game.table.bom.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.MurderEventRepository;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Реализация менеджера, отвечающего за формирование статистики
 */
@Service
@RequiredArgsConstructor
public class StatisticManagerImpl implements StatisticManager {

    private final GameDao gameDao;

    /**
     * Возвращает общую статистику
     *
     * @return список строк общей статистики
     */
    @Override
    public List<StatisticRow> getCommonInfo() {
        int kills = 0;
        int deaths = 0;
        Set<Player> players = new HashSet<>();
        int gamesCount = 0;
        int roundsCount = 0;

        List<Game> games = gameDao.findAll();
        for (Game game : games) {
            List<Round> rounds = game.getRounds();
            roundsCount += rounds.size();

            gamesCount++;

            for (Round round : rounds) {

                kills += round.getMurders().size();
            }
        }

        return null;
    }

    /**
     * Возвращает статистику игры
     *
     * @param gameUuid идентификатор игры
     * @return список строк статистики игры
     */
    @Override
    public List<StatisticRow> getGameInfo(UUID gameUuid) {
        return null;
    }

    /**
     * Возвращает статистику раунда игры
     *
     * @param gameUuid  идентификатор игры
     * @param roundUuid идентификатор раунда
     * @return список строк статистики раунда игры
     */
    @Override
    public List<StatisticRow> getRoundInfo(UUID gameUuid, UUID roundUuid) {
        return null;
    }

    /**
     * Возвращает статистку игрока в игре
     *
     * @param gameUuid   идентификатор игры
     * @param playerUuid идентификатор игрока
     * @return список строк статистики игрока в игре
     */
    @Override
    public List<StatisticRow> getGameInfoByPlayer(UUID gameUuid, UUID playerUuid) {
        return null;
    }

    /**
     * Возвращает статистику игрока в раунде игры
     *
     * @param gameUuid   идентификатор игры
     * @param roundUuid  идентификатор раунда
     * @param playerUuid идентификатор игрока
     * @return список строк статистики игрока в раунде игры
     */
    @Override
    public List<StatisticRow> getRoundInfoByPlayer(UUID gameUuid, UUID roundUuid, UUID playerUuid) {
        return null;
    }

    /**
     * Возвращает общую статистику игрока
     *
     * @param playerUuid идентификатор игрока
     * @return спискок строк статистики игрока
     */
    @Override
    public List<StatisticRow> getPlayerInfo(UUID playerUuid) {
        return null;
    }
}
