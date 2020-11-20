package ru.dilgorp.java.stats.game.table.bom.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.UUID;

/**
 * Маг, участник игры
 */
@Data
@NoArgsConstructor
public class Magician{
    private UUID uuid;
    private Player player;

    public Magician(Player player) {
        this.player = player;
    }
}
