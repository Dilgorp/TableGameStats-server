package ru.dilgorp.java.stats.game.table.bom.statistic.impl;

import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.StatisticRow;
import ru.dilgorp.java.stats.game.table.bom.statistic.StatisticManager;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.List;
import java.util.Set;

/**
 * Формирует строки статистики
 */
@Service
public class StatisticManagerImpl implements StatisticManager {

    /**
     * Добавляет в строку статистики информацию по убийстам и смертям в раунде.
     * Также добавляет игроков раунда в множество.
     *
     * @param statisticRow Строка статистики
     * @param players      Множество игроков
     * @param rounds       Список раундов
     */
    @Override
    public void addRoundStatistic(StatisticRow statisticRow, Set<Player> players, List<Round> rounds) {
        rounds.forEach(round -> {
            int kills = round.getMurders().size();
            statisticRow.setKills(statisticRow.getKills() + kills);
            statisticRow.setDeaths(statisticRow.getKills());
            copyPlayers(players, round);
        });
    }

    private void copyPlayers(Set<Player> players, Round round) {
        round.getParticipants().forEach(participant ->
                players.add(participant.getPlayer())
        );
    }
}
