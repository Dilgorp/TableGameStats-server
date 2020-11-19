package ru.dilgorp.java.stats.game.table.bom.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.java.stats.game.table.domain.Player;

import javax.persistence.*;
import java.util.UUID;

/**
 * Маг, участник игры
 */
@Data
@NoArgsConstructor
@Entity(name = "bom_magician")
public class Magician{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "player_uuid", referencedColumnName = "uuid")
    private Player player;
}
