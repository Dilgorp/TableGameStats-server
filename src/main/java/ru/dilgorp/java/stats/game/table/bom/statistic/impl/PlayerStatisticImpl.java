package ru.dilgorp.java.stats.game.table.bom.statistic.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.dao.GameDao;
import ru.dilgorp.java.stats.game.table.bom.dao.RoundDao;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;
import ru.dilgorp.java.stats.game.table.bom.statistic.PlayerStatistic;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.*;

/**
 * Формирует статистику по игрокам
 */
@Service
@RequiredArgsConstructor
public class PlayerStatisticImpl implements PlayerStatistic {

    private final GameDao gameDao;
    private final RoundDao roundDao;

    /**
     * Возвращает общую статистику по игрокам за все игры
     * По-умолчанию статистика отсортирована по убыванию убийств
     *
     * @return Список строк статистики по игрокам
     */
    @Override
    public List<StatisticRow> commonInfo() {
        return getStatisticResult(getCommonStatistic());
    }

    /**
     * Возвращает статистику по игре сгруппированную по игрокам
     *
     * @param gameUuid Идентификатор игры
     * @return Список строк статистики игры
     */
    @Override
    public List<StatisticRow> forGame(UUID gameUuid) {
        Map<Player, StatisticRow> statistic = new HashMap<>();

        Optional<Game> byId = gameDao.findById(gameUuid);
        if(byId.isEmpty()){
            return getStatisticResult(statistic);
        }

        byId.get().getRounds()
                .forEach(round -> round.getMurders()
                        .forEach(murderEvent -> addEventStatistic(statistic, murderEvent)));

        return getStatisticResult(statistic);
    }

    /**
     * Возвращает статистику раунда сгруппированную по игрокам
     *
     * @param roundUuid Идентификатор раунда
     * @return Список строк статистики раунда
     */
    @Override
    public List<StatisticRow> forRound(UUID roundUuid) {
        Map<Player, StatisticRow> statistic = new HashMap<>();

        Optional<Round> byId = roundDao.findById(roundUuid);
        if(byId.isEmpty()){
            return getStatisticResult(statistic);
        }

        byId.get().getMurders().forEach(murderEvent -> addEventStatistic(statistic, murderEvent));

        return getStatisticResult(statistic);
    }

    /**
     * Возвращает статистку игрока
     *
     * @param playerUuid идентификатор игрока
     * @return Статистика игрока
     */
    @Override
    public StatisticRow forPlayer(UUID playerUuid) {
        Map<Player, StatisticRow> commonStatistic = getCommonStatistic();

        Optional<Player> first = commonStatistic.keySet().stream()
                .filter(player -> player.getUuid().equals(playerUuid)).findFirst();

        if(first.isEmpty()){
            return new StatisticRow();
        }

        return commonStatistic.get(first.get());
    }

    private Map<Player, StatisticRow> getCommonStatistic() {
        Map<Player, StatisticRow> statistic = new HashMap<>();

        List<Game> games = gameDao.findAll();
        games.forEach(game -> game.getRounds()
                .forEach(round -> round.getMurders()
                        .forEach(murderEvent -> addEventStatistic(statistic, murderEvent))));
        return statistic;
    }

    private void addEventStatistic(Map<Player, StatisticRow> statistic, MurderEvent murderEvent) {
        Player initiator = murderEvent.getInitiator().getPlayer();
        StatisticRow initiatorRow = getStatisticRow(statistic, initiator);

        Player target = murderEvent.getTarget().getPlayer();
        StatisticRow targetRow = getStatisticRow(statistic, target);

        int quantity = murderEvent.getQuantity();
        initiatorRow.setKills(initiatorRow.getKills() + quantity);
        targetRow.setDeaths(targetRow.getDeaths() + quantity);

        statistic.put(initiator, initiatorRow);
        statistic.put(target, targetRow);
    }

    private StatisticRow getStatisticRow(Map<Player, StatisticRow> statistic, Player player) {
        StatisticRow statisticRow = statistic.get(player);
        if (statisticRow == null) {
            statisticRow = new StatisticRow();
            statisticRow.setPlayer(player);
        }
        return statisticRow;
    }

    private List<StatisticRow> getStatisticResult(Map<Player, StatisticRow> statistic) {
        List<StatisticRow> result = new ArrayList<>(statistic.values());
        result.sort((o1, o2) -> o2.getKills() - o1.getKills());
        return result;
    }
}
