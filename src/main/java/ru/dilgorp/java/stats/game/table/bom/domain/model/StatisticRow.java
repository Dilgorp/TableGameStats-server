package ru.dilgorp.java.stats.game.table.bom.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.UUID;

/**
 * Строка статистики
 */
@Data
@NoArgsConstructor
public class StatisticRow {
    private Player player;
    private int kills;
    private int deaths;
    private int players;
    private int games;
    private int rounds;

    private UUID gameUuid;
    private UUID roundUuid;

    public boolean isEmpty() {
        return kills == 0 && deaths == 0 && players == 0 && games == 0 && rounds == 0;
    }
}
