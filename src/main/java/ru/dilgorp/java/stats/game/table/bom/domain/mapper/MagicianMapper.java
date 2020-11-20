package ru.dilgorp.java.stats.game.table.bom.domain.mapper;

import org.springframework.stereotype.Component;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;

/**
 * Отвечает за преобразование магов
 */
@Component
public class MagicianMapper implements Mapper<MagicianEntity, Magician> {

    /**
     * Преобразует {@link Magician} в {@link MagicianEntity}
     *
     * @param model Объект модели {@link Magician}
     * @return Сущность базы данных {@link MagicianEntity}
     */
    @Override
    public MagicianEntity toEntity(Magician model) {
        if (model == null) {
            return null;
        }

        MagicianEntity magicianEntity = new MagicianEntity();
        magicianEntity.setPlayer(model.getPlayer());
        magicianEntity.setUuid(model.getUuid());
        return magicianEntity;
    }

    /**
     * Преобразует {@link MagicianEntity} в {@link Magician}
     *
     * @param entity Сущность базы данных {@link MagicianEntity}
     * @return Объект модели {@link Magician}
     */
    @Override
    public Magician toModel(MagicianEntity entity) {
        if (entity == null) {
            return null;
        }

        Magician magician = new Magician();
        magician.setPlayer(entity.getPlayer());
        magician.setUuid(entity.getUuid());
        return magician;
    }
}
