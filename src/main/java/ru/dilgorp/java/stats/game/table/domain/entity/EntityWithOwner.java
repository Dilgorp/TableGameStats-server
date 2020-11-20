package ru.dilgorp.java.stats.game.table.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность базы данных, у которой есть владелец
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@Getter
@Setter
public abstract class EntityWithOwner<O> extends EntityDB{
    @ManyToOne
    @JoinColumn(name = "owner_uuid", referencedColumnName = "uuid")
    protected O owner;
}
