package ru.dilgorp.java.stats.game.table.bom.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dilgorp.java.stats.game.table.domain.entity.EntityWithOwner;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Сущность участника раунда
 */
@Entity
@Table(name = "bom_round_participant")
@NoArgsConstructor
@Setter
@Getter
public class RoundParticipantEntity extends EntityWithOwner<RoundEntity> {
    @OneToOne
    @JoinColumn(name = "magician_uuid", referencedColumnName = "uuid")
    private MagicianEntity magician;
}
