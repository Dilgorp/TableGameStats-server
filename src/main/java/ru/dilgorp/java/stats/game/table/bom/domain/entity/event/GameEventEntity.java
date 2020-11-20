package ru.dilgorp.java.stats.game.table.bom.domain.entity.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.domain.entity.EntityWithOwner;

import javax.persistence.*;

/**
 * Сущность игрового события
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Getter
@Setter
public abstract class GameEventEntity extends EntityWithOwner<RoundEntity> {
    @OneToOne
    @JoinColumn(name = "initiator_uuid", referencedColumnName = "uuid")
    private MagicianEntity initiator;

    @OneToOne
    @JoinColumn(name = "target_uuid", referencedColumnName = "uuid")
    private MagicianEntity target;

    @Column(name = "quantity")
    private int quantity;
}
