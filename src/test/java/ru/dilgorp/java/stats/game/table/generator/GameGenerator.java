package ru.dilgorp.java.stats.game.table.generator;

import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Генератор игр для тестов
 */
public class GameGenerator {

    private final RoundGenerator roundGenerator;

    public GameGenerator(RoundGenerator roundGenerator) {
        this.roundGenerator = roundGenerator;
    }

    /**
     * Генерирует список игр
     *
     * @param count Количество игр, которые нужно сгенерировать
     * @return Список сгенерированных игр
     */
    public List<Game> generate(int count) {
        List<Game> games = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Game game = new Game();
            game.setUuid(UUID.randomUUID());
            game.setRounds(roundGenerator.generate(2));
            games.add(game);
        }
        return games;
    }
}
