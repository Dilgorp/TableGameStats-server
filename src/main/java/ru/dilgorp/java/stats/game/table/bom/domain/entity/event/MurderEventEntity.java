package ru.dilgorp.java.stats.game.table.bom.domain.entity.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Сущность убийстива
 */
@Entity
@Table(name = "bom_murders")
@NoArgsConstructor
@Getter
@Setter
public class MurderEventEntity extends GameEventEntity {
}
