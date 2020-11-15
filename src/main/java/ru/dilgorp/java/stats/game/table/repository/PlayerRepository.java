package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dilgorp.java.stats.game.table.domain.Player;

public interface PlayerRepository extends MongoRepository<Player, String> {
    Player findByName(String name);
}
