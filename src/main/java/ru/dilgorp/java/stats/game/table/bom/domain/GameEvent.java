package ru.dilgorp.java.stats.game.table.bom.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

/**
 * Игровое событие в раунде
 */
@Data
@NoArgsConstructor
@Entity(name = "bom_event")
public class GameEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "round_uuid", referencedColumnName = "uuid")
    private Round round;

    @OneToOne
    @JoinColumn(name = "initiator_uuid", referencedColumnName = "uuid")
    private Magician initiator;

    @OneToOne
    @JoinColumn(name = "target_uuid", referencedColumnName = "uuid")
    private Magician target;

    private int quantity;
}
