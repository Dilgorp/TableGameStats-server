package ru.dilgorp.java.stats.game.table.bom.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.event.MurderEventEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;

/**
 * Отвечает за преобразование раундов
 */
@Component
@RequiredArgsConstructor
public class RoundMapper implements MapperWithOwner<RoundEntity, Round, GameEntity> {

    private final MapperWithOwner<MurderEventEntity, MurderEvent, RoundEntity> murderEventMapper;
    private final Mapper<MagicianEntity, Magician> magicianMapper;

    /**
     * Преобразовывает {@link Round} в {@link RoundEntity}
     *
     * @param model Объект модели {@link Round}
     * @param owner Владелец сущности {@link GameEntity}
     * @return сущность базы данных {@link RoundEntity}
     */
    @Override
    public RoundEntity toEntity(Round model, GameEntity owner) {
        if (model == null) {
            return null;
        }

        RoundEntity roundEntity = new RoundEntity();
        roundEntity.setUuid(model.getUuid());
        roundEntity.setWinner(magicianMapper.toEntity(model.getWinner()));
        roundEntity.setOwner(owner);
        roundEntity.setStart(model.getStart());
        roundEntity.setStop(model.getStop());
        roundEntity.setMurders(murderEventMapper.modelsToEntities(model.getMurders(), roundEntity));
        return roundEntity;
    }

    /**
     * Преобразовывает {@link RoundEntity} в {@link Round}
     *
     * @param entity Сущность базы данных {@link RoundEntity}
     * @return Объект модели {@link Round}
     */
    @Override
    public Round toModel(RoundEntity entity) {
        if (entity == null) {
            return null;
        }

        Round round = new Round();
        round.setUuid(entity.getUuid());
        round.setStart(entity.getStart());
        round.setStop(entity.getStop());
        round.setWinner(magicianMapper.toModel(entity.getWinner()));
        round.setMurders(murderEventMapper.entitiesToModels(entity.getMurders()));
        return round;
    }
}
