package ru.dilgorp.java.stats.game.table.bom.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Состявшаяся игра
 */
@Data
@NoArgsConstructor
public class Game {
    private UUID uuid;
    private Date start;
    private Date stop;
    private List<Round> rounds;
    private Magician winner;
}
