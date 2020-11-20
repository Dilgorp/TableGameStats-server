package ru.dilgorp.java.stats.game.table.bom.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dilgorp.java.stats.game.table.domain.entity.EntityDB;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Сущность игры
 */
@Entity
@Table(name = "bom_games")
@NoArgsConstructor
@Getter
@Setter
public class GameEntity extends EntityDB {

    @Column(name = "start")
    private Date start;

    @Column(name = "stop")
    private Date stop;

    @OneToMany(mappedBy = "owner")
    private List<RoundEntity> rounds;

    @OneToOne
    @JoinColumn(name = "winner_uuid", referencedColumnName = "uuid")
    private MagicianEntity winner;
}
