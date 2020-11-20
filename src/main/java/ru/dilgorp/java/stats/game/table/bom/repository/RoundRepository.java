package ru.dilgorp.java.stats.game.table.bom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoundRepository extends JpaRepository<RoundEntity, UUID> {

    /**
     * Возвращает список раунде по игре
     *
     * @param owner Игра
     * @return Список раундов
     */
    List<RoundEntity> findByOwner(GameEntity owner);
}
