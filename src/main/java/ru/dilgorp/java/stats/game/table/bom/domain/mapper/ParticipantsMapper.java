package ru.dilgorp.java.stats.game.table.bom.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundParticipantEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;

/**
 * Отвечает за преобразование участников раунда
 */
@RequiredArgsConstructor
@Component
public class ParticipantsMapper implements MapperWithOwner<RoundParticipantEntity, Magician, RoundEntity> {

    private final Mapper<MagicianEntity, Magician> magicianMapper;

    /**
     * Преобразовывает {@link Magician} в {@link RoundParticipantEntity}
     *
     * @param model Объект модели {@link Magician}
     * @param owner Владелец сущности {@link RoundEntity}
     * @return сущность базы данных {@link RoundParticipantEntity}
     */
    @Override
    public RoundParticipantEntity toEntity(Magician model, RoundEntity owner) {
        if (model == null) {
            return null;
        }

        RoundParticipantEntity entity = new RoundParticipantEntity();
        entity.setUuid(model.getUuid());
        entity.setMagician(magicianMapper.toEntity(model));
        entity.setOwner(owner);

        return entity;
    }

    /**
     * Преобразовывает {@link RoundParticipantEntity} в {@link Magician}
     *
     * @param entity Сущность базы данных {@link RoundParticipantEntity}
     * @return Объект модели {@link Magician}
     */
    @Override
    public Magician toModel(RoundParticipantEntity entity) {
        Magician model = new Magician(entity.getMagician().getPlayer());
        model.setUuid(entity.getUuid());
        return model;
    }
}
