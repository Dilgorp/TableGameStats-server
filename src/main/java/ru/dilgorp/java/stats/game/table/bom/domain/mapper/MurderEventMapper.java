package ru.dilgorp.java.stats.game.table.bom.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.event.MurderEventEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;

/**
 * Отвечает за преобразование убийств
 */
@RequiredArgsConstructor
@Component
public class MurderEventMapper implements MapperWithOwner<MurderEventEntity, MurderEvent, RoundEntity> {

    private final Mapper<MagicianEntity, Magician> magicianMapper;

    /**
     * Преобразовывает {@link MurderEvent} в {@link MurderEventEntity}
     *
     * @param model Объект модели {@link MurderEvent}
     * @param owner Владелец сущности {@link RoundEntity}
     * @return Сущность базы данных {@link MurderEventEntity}
     */
    @Override
    public MurderEventEntity toEntity(MurderEvent model, RoundEntity owner) {
        if (model == null) {
            return null;
        }

        MurderEventEntity murderEventEntity = new MurderEventEntity();
        murderEventEntity.setOwner(owner);
        murderEventEntity.setUuid(model.getUuid());
        murderEventEntity.setInitiator(magicianMapper.toEntity(model.getInitiator()));
        murderEventEntity.setTarget(magicianMapper.toEntity(model.getTarget()));
        murderEventEntity.setQuantity(model.getQuantity());
        return murderEventEntity;
    }

    /**
     * Преобразовывает {@link MurderEventEntity} в {@link MurderEvent}
     *
     * @param entity Сущность базы данных {@link MurderEventEntity}
     * @return Объект модели {@link MurderEvent}
     */
    @Override
    public MurderEvent toModel(MurderEventEntity entity) {
        if (entity == null) {
            return null;
        }

        MurderEvent murderEvent = new MurderEvent();
        murderEvent.setUuid(entity.getUuid());
        murderEvent.setInitiator(magicianMapper.toModel(entity.getInitiator()));
        murderEvent.setTarget(magicianMapper.toModel(entity.getTarget()));
        murderEvent.setQuantity(entity.getQuantity());
        return murderEvent;
    }
}
