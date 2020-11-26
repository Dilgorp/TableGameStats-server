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

    public RoundGenerator(MurderEventGenerator murderEventGenerator) {
        this.murderEventGenerator = murderEventGenerator;
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
            rounds.add(round);
        }
        return rounds;
    }
}
