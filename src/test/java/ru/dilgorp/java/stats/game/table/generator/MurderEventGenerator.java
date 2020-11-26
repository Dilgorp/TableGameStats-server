package ru.dilgorp.java.stats.game.table.generator;

import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Генератор событий убийств
 */
public class MurderEventGenerator {

    private final MagicianGenerator magicianGenerator;

    public MurderEventGenerator(MagicianGenerator magicianGenerator) {
        this.magicianGenerator = magicianGenerator;
    }

    /**
     * Генериурет список событий-убийств
     *
     * @param count Количество событий, которое нужно сгенерировать
     * @return Сгенерированный список событий
     */
    public List<MurderEvent> generate(int count) {
        List<MurderEvent> events = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            List<Magician> magicians = magicianGenerator.generate(2);
            MurderEvent murderEvent = new MurderEvent(magicians.get(0), magicians.get(1), 1);
            murderEvent.setUuid(UUID.randomUUID());
            events.add(murderEvent);
        }
        return events;
    }
}
