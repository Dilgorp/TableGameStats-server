package ru.dilgorp.java.stats.game.table.bom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.event.MurderEventEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface MurderEventRepository extends JpaRepository<MurderEventEntity, UUID> {

    /**
     * Возвращает список событий-убийств в раунде
     *
     * @param owner Раунд
     * @return Список событий-убийства
     */
    List<MurderEventEntity> findByOwner(RoundEntity owner);
}
