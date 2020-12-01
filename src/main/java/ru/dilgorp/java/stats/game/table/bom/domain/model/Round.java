package ru.dilgorp.java.stats.game.table.bom.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Раунд состоявшейся игры
 */
@Data
@NoArgsConstructor
public class Round {
    private UUID uuid;
    private Date start;
    private Date stop;
    private Magician winner;
    private List<MurderEvent> murders;
    private List<Magician> participants;
}
