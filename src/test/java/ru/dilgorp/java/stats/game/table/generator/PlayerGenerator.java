package ru.dilgorp.java.stats.game.table.generator;

import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, генерирующий игроков для тестов
 */
public class PlayerGenerator {

    /**
     * Генерирует списое игроков для тестов
     * @param count количество игроков в списке
     * @return список игроков
     */
    public List<Player> generate(int count){
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < count; i++){
            Player player = new Player();

            player.setId(Integer.toString(i));
            player.setName(Integer.toString(i));

            players.add(player);
        }

        return players;
    }

    /**
     * Возвращает копию игрока
     * @param player игрок, которого нужно скопировать
     * @return копия игрока
     */
    public Player copyOf(Player player){
        Player copy = new Player();
        copy.setId(player.getId());
        copy.setName(player.getName());
        return copy;
    }
}
