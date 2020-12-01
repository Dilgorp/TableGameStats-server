package ru.dilgorp.java.stats.game.table.generator;

import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс генерирующий рануды
 */
public class RoundGenerator {

    private final MurderEventGenerator murderEventGenerator;
    private final MagicianGenerator magicianGenerator;

    public RoundGenerator(
            MurderEventGenerator murderEventGenerator,
            MagicianGenerator magicianGenerator
    ) {
        this.murderEventGenerator = murderEventGenerator;
        this.magicianGenerator = magicianGenerator;
    }

    /**
     * Генерирует список раундов для тестов
     *
     * @param count Количество раундов, которое нужно сгенерировать
     * @return Список сгенерированных раундов
     */
    public List<Round> generate(int count) {
        List<Round> rounds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Round round = new Round();
            round.setUuid(UUID.randomUUID());
            round.setMurders(murderEventGenerator.generate(3));
            round.setParticipants(magicianGenerator.generate(2));
            rounds.add(round);
        }
        return rounds;
    }
}
