package ru.dilgorp.java.stats.game.table.bom.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Строка статистики
 */
@Data
@NoArgsConstructor
public class StatisticRow {
    private int kills;
    private int deaths;
    private int players;
    private int games;
    private int rounds;

    private UUID gameUuid;
    private UUID roundUuid;
    private UUID magicianUuid;
}
