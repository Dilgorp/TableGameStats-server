package ru.dilgorp.java.stats.game.table.bom.statistic.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.statistic.GameStatistic;
import ru.dilgorp.java.stats.game.table.bom.statistic.StatisticManager;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.*;

/**
 * Реализация менеджера, отвечающего за формирование статистики
 */
@Service
@RequiredArgsConstructor
public class GameStatisticImpl implements GameStatistic {

    private final StatisticManager statisticManager;
    private final GameDao gameDao;

    /**
     * Возвращает общую статистику
     *
     * @return список строк общей статистики
     */
    @Override
    public StatisticRow getCommonInfo() {
        Set<Player> players = new HashSet<>();

        StatisticRow statisticRow = new StatisticRow();
        gameDao.findAll().forEach(game -> {
            List<Round> rounds = game.getRounds();
            statisticRow.setRounds(statisticRow.getRounds() + rounds.size());
            statisticRow.setGames(statisticRow.getGames() + 1);
            statisticManager.addRoundStatistic(statisticRow, players, rounds);
        });

        statisticRow.setPlayers(players.size());
        return statisticRow;
    }

    /**
     * Возвращает общую статистику сгруппированную по играм
     *
     * @return список строк статистики по играм
     */
    @Override
    public List<StatisticRow> byGames() {
        List<StatisticRow> statisticRows = new ArrayList<>();

        Set<Player> players = new HashSet<>();

        List<Game> games = gameDao.findAll();
        for (Game game : games) {
            StatisticRow statisticRow = new StatisticRow();
            List<Round> rounds = game.getRounds();

            statisticManager.addRoundStatistic(statisticRow, players, rounds);
            setGameStatistic(players, statisticRow, rounds, game.getUuid());
            statisticRows.add(statisticRow);

            players.clear();
        }

        return statisticRows;
    }

    /**
     * Возвращает статистику игры
     *
     * @param gameUuid идентификатор игры
     * @return статистика игры
     */
    @Override
    public StatisticRow gameInfo(UUID gameUuid) {
        StatisticRow statisticRow = new StatisticRow();

        Optional<Game> byId = gameDao.findById(gameUuid);
        if (byId.isEmpty()) {
            return statisticRow;
        }

        Set<Player> players = new HashSet<>();
        Game game = byId.get();
        statisticManager.addRoundStatistic(statisticRow, players, game.getRounds());

        setGameStatistic(players, statisticRow, game.getRounds(), gameUuid);

        return statisticRow;
    }

    private void setGameStatistic(
            Set<Player> players,
            StatisticRow statisticRow,
            List<Round> rounds,
            UUID uuid
    ) {
        statisticRow.setPlayers(players.size());
        statisticRow.setGames(1);
        statisticRow.setRounds(rounds.size());
        statisticRow.setGameUuid(uuid);
    }
}
