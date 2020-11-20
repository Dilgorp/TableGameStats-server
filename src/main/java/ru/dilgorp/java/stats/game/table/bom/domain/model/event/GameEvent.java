package ru.dilgorp.java.stats.game.table.bom.domain.model.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;

import java.util.UUID;

/**
 * Игровое событие в раунде
 */
@Data
@NoArgsConstructor
public abstract class GameEvent {
    private UUID uuid;
    private Magician initiator;
    private Magician target;
    private int quantity;
}
