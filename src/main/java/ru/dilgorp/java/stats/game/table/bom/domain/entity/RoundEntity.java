package ru.dilgorp.java.stats.game.table.bom.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.event.MurderEventEntity;
import ru.dilgorp.java.stats.game.table.domain.entity.EntityWithOwner;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Сущность раунда
 */
@Entity
@Table(name = "bom_rounds")
@NoArgsConstructor
@Getter
@Setter
public class RoundEntity extends EntityWithOwner<GameEntity> {

    @Column(name = "start")
    private Date start;

    @Column(name = "stop")
    private Date stop;

    @OneToOne
    @JoinColumn(name = "winner_uuid", referencedColumnName = "uuid")
    private MagicianEntity winner;

    @OneToMany(mappedBy = "owner")
    private List<MurderEventEntity> murders;

    @OneToMany(mappedBy = "owner")
    private List<RoundParticipantEntity> participants;
}
