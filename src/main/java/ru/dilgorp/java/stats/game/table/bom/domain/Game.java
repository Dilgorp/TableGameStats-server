package ru.dilgorp.java.stats.game.table.bom.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Состявшаяся игра
 */
@Data
@NoArgsConstructor
@Entity(name = "bom_game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private Date start;
    private Date stop;

    @OneToMany(mappedBy = "game")
    private List<Round> rounds;

    @OneToOne
    @JoinColumn(name = "winner_uuid", referencedColumnName = "uuid")
    private Magician winner;
}
