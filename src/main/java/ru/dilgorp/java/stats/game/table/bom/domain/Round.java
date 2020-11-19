package ru.dilgorp.java.stats.game.table.bom.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Раунд состоявшейся игры
 */
@Data
@NoArgsConstructor
@Entity(name = "bom_round")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private Date start;
    private Date stop;

    @OneToOne
    @JoinColumn(name = "winner_uuid", referencedColumnName = "uuid")
    private Magician winner;

    @ManyToOne
    @JoinColumn(name = "game_uuid", referencedColumnName = "uuid")
    private Game game;

    @OneToMany(mappedBy = "round")
    private List<GameEvent> murders;
}
