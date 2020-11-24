package ru.dilgorp.java.stats.game.table.generator;

import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Класс, генерирующий сущности магов
 */
public class MagicianGenerator {

    private final PlayerGenerator playerGenerator;

    public MagicianGenerator(PlayerGenerator playerGenerator) {
        this.playerGenerator = playerGenerator;
    }

    /**
     * Генерирует список сущностей магов
     * @param count Необходимое количество сущностей в списке
     * @return Список сгенерированных сущностей
     */
    public List<Magician> generate(int count) {
        List<Magician> magicians = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Player player = new Player();
            player.setUuid(UUID.randomUUID());
            player.setName(Integer.toString(i));

            Magician mag = new Magician();
            mag.setUuid(UUID.randomUUID());
            mag.setPlayer(player);
            magicians.add(mag);
        }
        return magicians;
    }

    /**
     * Копирует переданную сущность-мага
     * @param magician Сущность-маг, которую нужно скопировать
     * @return Копия переданной сущности-мага
     */
    public Magician copyOf(Magician magician){
        Player player = playerGenerator.copyOf(magician.getPlayer());
        Magician mag = new Magician();
        mag.setUuid(UUID.fromString(magician.getUuid().toString()));
        mag.setPlayer(player);

        return mag;
    }
}
