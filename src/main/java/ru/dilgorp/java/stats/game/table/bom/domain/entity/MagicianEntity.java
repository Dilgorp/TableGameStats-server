package ru.dilgorp.java.stats.game.table.bom.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dilgorp.java.stats.game.table.domain.Player;
import ru.dilgorp.java.stats.game.table.domain.entity.EntityDB;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Сущность мага
 */
@Entity
@Table(name = "bom_magicians")
@NoArgsConstructor
@Setter
@Getter
public class MagicianEntity extends EntityDB {
    @OneToOne
    @JoinColumn(name = "player_uuid", referencedColumnName = "uuid")
    private Player player;
}
