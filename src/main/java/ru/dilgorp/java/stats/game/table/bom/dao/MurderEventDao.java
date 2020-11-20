package ru.dilgorp.java.stats.game.table.bom.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.event.MurderEventEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;
import ru.dilgorp.java.stats.game.table.bom.repository.MurderEventRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.dao.DaoWithOwner;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Обеспечивает работу с данными базы по модели {@link MurderEvent}
 */
@Service
@RequiredArgsConstructor
public class MurderEventDao implements DaoWithOwner<MurderEvent, UUID, Round> {

    private final MurderEventRepository murderEventRepository;
    private final RoundRepository roundRepository;

    private final MapperWithOwner<MurderEventEntity, MurderEvent, RoundEntity> murderEventMapper;
    private final MapperWithOwner<RoundEntity, Round, GameEntity> roundMapper;

    private final Dao<Magician, UUID> magicianDao;

    /**
     * Возвращает список событий-убийств {@link MurderEvent} в раунде {@link Round}
     *
     * @param owner Раунд-владелец событий {@link Round}
     * @return Список событий в раунде
     */
    @Override
    public List<MurderEvent> findByOwner(Round owner) {
        GameEntity gameEntity = roundRepository.getOne(owner.getUuid()).getOwner();
        return murderEventMapper.entitiesToModels(
                murderEventRepository.findByOwner(
                        roundMapper.toEntity(owner, gameEntity)
                )
        );
    }

    /**
     * Сохраняет список событий-убийств {@link MurderEvent} по владельцу {@link Round}
     *
     * @param models Список событий-убийств {@link MurderEvent}
     * @param owner  Раунд-владелец событий {@link Round}
     * @return Сохраненный список событий
     */
    @Override
    public List<MurderEvent> saveByOwner(List<MurderEvent> models, Round owner) {
        // Получаем игру-сущность, в которой произошло событие
        GameEntity gameEntity = roundRepository.getOne(owner.getUuid()).getOwner();

        // Получаем раунд-сущность
        RoundEntity roundEntity = roundMapper.toEntity(owner, gameEntity);

        List<MurderEvent> savedEvents = new ArrayList<>();

        // Сохраняем события
        models.forEach(murderEvent -> {
            // Сохраняем инициатора, если еще не сохранен
            if (murderEvent.getInitiator() != null && murderEvent.getInitiator().getUuid() == null) {
                murderEvent.setInitiator(magicianDao.save(murderEvent.getInitiator()));
            }

            // Сохраняем цель, если еще не сохранена
            if (murderEvent.getTarget() != null && murderEvent.getTarget().getUuid() == null) {
                murderEvent.setTarget(magicianDao.save(murderEvent.getTarget()));
            }

            // Сохраняем событие
            MurderEvent savedEvent = murderEventMapper.toModel(
                    murderEventRepository.save(
                            murderEventMapper.toEntity(murderEvent, roundEntity)
                    )
            );

            savedEvents.add(savedEvent);
        });

        // Возвращаем сохраненные данные
        return savedEvents;
    }

    /**
     * Сохраняет событие-убийство {@link MurderEvent} по владельцу {@link Round}
     *
     * @param model Событие-убийство {@link MurderEvent}
     * @param owner Раунд-владелец событий {@link Round}
     * @return Сохраненное событие
     */
    @Override
    public MurderEvent save(MurderEvent model, Round owner) {
        GameEntity gameEntity = roundRepository.getOne(owner.getUuid()).getOwner();
        return murderEventMapper.toModel(
                murderEventRepository.save(
                        murderEventMapper.toEntity(
                                model,
                                roundMapper.toEntity(owner, gameEntity)
                        )
                )
        );
    }

    /**
     * Возвращает список всех событий убийств {@link MurderEvent}
     *
     * @return Список всех событий-убийств
     */
    @Override
    public List<MurderEvent> findAll() {
        return murderEventMapper.entitiesToModels(murderEventRepository.findAll());
    }

    /**
     * Возвращает событие убийство {@link MurderEvent} по идетификатору {@link UUID}
     *
     * @param uuid Идентификатор события
     * @return Событие-убийство
     */
    @Override
    public Optional<MurderEvent> findById(UUID uuid) {
        return murderEventRepository.findById(uuid).map(murderEventMapper::toModel);
    }

    /**
     * Удаляет событие убийство {@link MurderEvent} по идетификатору {@link UUID}
     *
     * @param uuid Идентификатор события
     */
    @Override
    public void delete(UUID uuid) {
        murderEventRepository.deleteById(uuid);
    }
}
