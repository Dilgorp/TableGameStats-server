package ru.dilgorp.java.stats.game.table.bom.statistic.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.dao.RoundDao;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.statistic.RoundStatistic;
import ru.dilgorp.java.stats.game.table.bom.statistic.StatisticManager;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.*;

/**
 * Формирует статистику по раундам
 */
@Service
@RequiredArgsConstructor
public class RoundStatisticImpl implements RoundStatistic {

    private final StatisticManager statisticManager;

    private final GameDao gameDao;
    private final RoundDao roundDao;

    /**
     * Возвращает статистику по раундам игры
     *
     * @param gameUuid Идентификатор игры
     * @return Список строк статистики по раундам
     */
    @Override
    public List<StatisticRow> byRounds(UUID gameUuid) {
        List<StatisticRow> statisticRows = new ArrayList<>();

        Optional<Game> byId = gameDao.findById(gameUuid);
        if (byId.isEmpty()) {
            return statisticRows;
        }

        Set<Player> players = new HashSet<>();
        for (Round round : byId.get().getRounds()) {
            StatisticRow statisticRow = new StatisticRow();
            statisticRow.setGameUuid(gameUuid);

            statisticManager.addRoundStatistic(statisticRow, players, List.of(round));

            setRoundStatistic(players, round.getUuid(), statisticRow);

            statisticRows.add(statisticRow);

            players.clear();
        }

        return statisticRows;
    }

    /**
     * Возвращает статистику раунда игры
     *
     * @param roundUuid Идентификатор раунда
     * @return список строк статистики раунда игры
     */
    @Override
    public StatisticRow roundInfo(UUID roundUuid) {
        StatisticRow statisticRow = new StatisticRow();

        Optional<Round> byId = roundDao.findById(roundUuid);
        if (byId.isEmpty()) {
            return statisticRow;
        }

        Set<Player> players = new HashSet<>();

        Round round = byId.get();
        statisticManager.addRoundStatistic(statisticRow, players, List.of(round));
        setRoundStatistic(players, round.getUuid(), statisticRow);

        return statisticRow;
    }

    private void setRoundStatistic(Set<Player> players, UUID roundUuid, StatisticRow statisticRow) {
        statisticRow.setRoundUuid(roundUuid);
        statisticRow.setRounds(1);
        statisticRow.setGames(1);
        statisticRow.setPlayers(players.size());
    }
}
