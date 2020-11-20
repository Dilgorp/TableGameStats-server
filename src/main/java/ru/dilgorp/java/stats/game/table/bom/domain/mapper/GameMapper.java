package ru.dilgorp.java.stats.game.table.bom.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;

/**
 * Отвечает за преобразование игр
 */
@Component
@RequiredArgsConstructor
public class GameMapper implements Mapper<GameEntity, Game> {

    private final MapperWithOwner<RoundEntity, Round, GameEntity> roundMapper;
    private final Mapper<MagicianEntity, Magician> magicianMapper;

    /**
     * Преобразует {@link Game} в {@link GameEntity}
     *
     * @param model Объект модели {@link Game}
     * @return Сущность базы данных {@link GameEntity}
     */
    @Override
    public GameEntity toEntity(Game model) {
        if (model == null) {
            return null;
        }

        GameEntity gameEntity = new GameEntity();
        gameEntity.setUuid(model.getUuid());
        gameEntity.setStart(model.getStart());
        gameEntity.setStop(model.getStop());
        gameEntity.setWinner(magicianMapper.toEntity(model.getWinner()));
        gameEntity.setRounds(roundMapper.modelsToEntities(model.getRounds(), gameEntity));
        return gameEntity;
    }

    /**
     * Преобразует {@link GameEntity} в {@link Game}
     *
     * @param entity Сущность базы данных {@link GameEntity}
     * @return Объект модели {@link Game}
     */
    @Override
    public Game toModel(GameEntity entity) {
        if (entity == null) {
            return null;
        }

        Game game = new Game();
        game.setUuid(entity.getUuid());
        game.setStart(entity.getStart());
        game.setStop(entity.getStop());
        game.setWinner(magicianMapper.toModel(entity.getWinner()));
        game.setRounds(roundMapper.entitiesToModels(entity.getRounds()));
        return game;
    }
}
