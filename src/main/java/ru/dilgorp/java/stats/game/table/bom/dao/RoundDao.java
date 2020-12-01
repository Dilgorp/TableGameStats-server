package ru.dilgorp.java.stats.game.table.bom.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.dao.DaoWithOwner;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Обеспечивает родоту с данными базы по модели {@link Round}
 */
@Service
@RequiredArgsConstructor
public class RoundDao implements DaoWithOwner<Round, UUID, Game> {

    private final RoundRepository roundRepository;

    private final Mapper<GameEntity, Game> gameMapper;
    private final MapperWithOwner<RoundEntity, Round, GameEntity> roundMapper;

    private final Dao<Magician, UUID> magicianDao;
    private final DaoWithOwner<MurderEvent, UUID, Round> murderEventDao;
    private final DaoWithOwner<Magician, UUID, Round> participantDao;

    /**
     * Возвращает список раундов {@link Round} по игре {@link Game}
     *
     * @param owner Игра-владелец {@link Game}
     * @return Список раудов {@link Round}
     */
    @Override
    public List<Round> findByOwner(Game owner) {
        return roundMapper.entitiesToModels(
                roundRepository.findByOwner(gameMapper.toEntity(owner))
        );
    }

    /**
     * Сохраняет список раундов {@link Round} по игре {@link Game}
     *
     * @param models Список раудов {@link Round}
     * @param owner  Игра-владелец {@link Game}
     * @return Сохраненный список раудов {@link Round}
     */
    @Override
    public List<Round> saveByOwner(List<Round> models, Game owner) {
        // Получаем игру-сущность
        GameEntity gameEntity = gameMapper.toEntity(owner);

        List<Round> savedRounds = new ArrayList<>();

        // Сохраняем раунды-сущности в базу
        models.forEach(round -> {

            // Сохраняем мага, если еще не сохранен
            if (round.getWinner() != null && round.getWinner().getUuid() == null) {
                round.setWinner(magicianDao.save(round.getWinner()));
            }

            // Сохраняем раунд
            Round savedRound = roundMapper.toModel(
                    roundRepository.save(
                            roundMapper.toEntity(round, gameEntity)
                    )
            );

            // Сохраняем убийства и помещаем их в сохраненный раунд
            savedRound.setMurders(
                    murderEventDao.saveByOwner(round.getMurders(), savedRound)
            );

            // Сохраняем участников раунда и помещаем их в сохраненный раунд
            savedRound.setParticipants(
                    participantDao.saveByOwner(round.getParticipants(), round)
            );

            savedRounds.add(savedRound);
        });

        // Возвращаем сохраненные раунды
        return savedRounds;
    }

    /**
     * Возвращает список всех раундов
     *
     * @return Список раудов {@link Round}
     */
    @Override
    public List<Round> findAll() {
        return roundMapper.entitiesToModels(roundRepository.findAll());
    }

    /**
     * Сохраняет раунд {@link Round} игры {@link Game}
     *
     * @param model Раунд игры {@link Round}
     * @param owner Игра {@link Game}
     * @return Сохраненный раунд игры {@link Round}
     */
    @Override
    public Round save(Round model, Game owner) {
        return roundMapper.toModel(
                roundRepository.save(
                        roundMapper.toEntity(
                                model,
                                gameMapper.toEntity(owner)
                        )
                )
        );
    }

    /**
     * Возвращает раунд {@link Round} по идентификатору {@link UUID}
     *
     * @param uuid Идентификатор раунда
     * @return Раунд игры {@link Round}
     */
    @Override
    public Optional<Round> findById(UUID uuid) {
        return roundRepository.findById(uuid).map(roundMapper::toModel);
    }

    /**
     * Удаляет раунд {@link Round} по идентификатору {@link UUID}
     *
     * @param uuid Идентификатор раунда
     */
    @Override
    public void delete(UUID uuid) {
        roundRepository.deleteById(uuid);
    }
}
