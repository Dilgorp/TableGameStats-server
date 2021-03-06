package ru.dilgorp.java.stats.game.table.bom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;

import java.util.UUID;

@Repository
public interface MagicianRepository extends JpaRepository<MagicianEntity, UUID> {
}
