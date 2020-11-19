package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
    Player findByName(String name);
}
