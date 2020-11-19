package ru.dilgorp.java.stats.game.table.bom.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.java.stats.game.table.domain.Player;

/**
 * Строка статистики
 */
@Data
@NoArgsConstructor
public class StatisticRow {
    private Player player;
    private int kills;
    private int deaths;

    private String gameId;
    private String roundId;
    private String magicianId;

    public StatisticRow(Player player) {
        this.player = player;
        kills = 0;
        deaths = 0;
    }
}
