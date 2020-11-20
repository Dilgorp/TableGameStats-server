package ru.dilgorp.java.stats.game.table.bom.domain.model.event;

import lombok.NoArgsConstructor;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;

/**
 * Убийство в раунде
 */
@NoArgsConstructor
public class MurderEvent extends GameEvent {
    public MurderEvent(Magician initiator, Magician target, int quality) {
        super();
        setInitiator(initiator);
        setTarget(target);
        setQuantity(quality);
    }
}
