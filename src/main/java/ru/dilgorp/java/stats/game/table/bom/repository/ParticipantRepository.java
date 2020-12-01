package ru.dilgorp.java.stats.game.table.bom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundParticipantEntity;

import java.util.List;
import java.util.UUID;

public interface ParticipantRepository extends JpaRepository<RoundParticipantEntity, UUID> {

    /**
     * Возвращает список участников в раунде
     *
     * @param owner Раунд
     * @return Список участников
     */
    List<RoundParticipantEntity> findByOwner(RoundEntity owner);
}
