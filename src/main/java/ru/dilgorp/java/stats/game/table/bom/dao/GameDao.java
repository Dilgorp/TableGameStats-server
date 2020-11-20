package ru.dilgorp.java.stats.game.table.bom.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.dao.DaoWithOwner;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Обеспечивает родоту с данными базы по модели {@link Game}
 */
@Service
@RequiredArgsConstructor
public class GameDao implements Dao<Game, UUID> {

    private final GameRepository gameRepository;

    private final Mapper<GameEntity, Game> gameMapper;

    private final Dao<Magician, UUID> magicianDao;
    private final DaoWithOwner<Round, UUID, Game> roundDao;

    /**
     * Возвращает список всех игр {@link Game}
     *
     * @return Список игр {@link Game}
     */
    @Override
    public List<Game> findAll() {
        return List.copyOf(gameMapper.entitiesToModels(gameRepository.findAll()));
    }

    /**
     * Сохраняет игру {@link Game} в базу данных
     *
     * @param model Игра {@link Game}
     * @return Игра {@link Game}
     */
    @Override
    public Game save(Game model) {

        // Сохраняем мага, если еще не сохранен
        if (model.getWinner() != null && model.getWinner().getUuid() == null) {
            model.setWinner(magicianDao.save(model.getWinner()));
        }

        // Сохраняем игру
        Game savedGame = gameMapper.toModel(gameRepository.save(gameMapper.toEntity(model)));

        // Сохраняем раунды в игре
        roundDao.saveByOwner(model.getRounds(), savedGame);

        // Возвращаем сохраненную игру
        return gameMapper.toModel(gameRepository.getOne(savedGame.getUuid()));
    }

    /**
     * Возвращает игру {@link Game} по идентификатору {@link UUID}
     *
     * @param uuid идентификатор {@link UUID} игры {@link Game}
     * @return Игра {@link Game}
     */
    @Override
    public Optional<Game> findById(UUID uuid) {
        return gameRepository.findById(uuid).map(gameMapper::toModel);
    }

    /**
     * Удаляет игру {@link Game} по идентификатору {@link UUID}
     *
     * @param uuid uuid идентификатор {@link UUID} игры {@link Game}
     */
    @Override
    public void delete(UUID uuid) {
        gameRepository.deleteById(uuid);
    }
}
