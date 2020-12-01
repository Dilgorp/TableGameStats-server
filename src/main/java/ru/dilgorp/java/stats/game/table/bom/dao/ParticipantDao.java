package ru.dilgorp.java.stats.game.table.bom.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundParticipantEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.repository.ParticipantRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.dao.DaoWithOwner;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Обеспечивает работу с данными базы по модели {@link Magician}
 */
@Service
@RequiredArgsConstructor
public class ParticipantDao implements DaoWithOwner<Magician, UUID, Round> {

    private final ParticipantRepository participantRepository;
    private final RoundRepository roundRepository;

    private final MapperWithOwner<RoundParticipantEntity, Magician, RoundEntity> participantsMapper;
    private final MapperWithOwner<RoundEntity, Round, GameEntity> roundMapper;

    private final Dao<Magician, UUID> magicianDao;

    /**
     * Возвращает список участников {@link Magician} в раунде {@link Round}
     *
     * @param owner Раунд-владелец событий {@link Round}
     * @return Список участников в раунде
     */
    @Override
    public List<Magician> findByOwner(Round owner) {
        GameEntity gameEntity = roundRepository.getOne(owner.getUuid()).getOwner();
        return participantsMapper.entitiesToModels(
                participantRepository.findByOwner(
                        roundMapper.toEntity(owner, gameEntity)
                )
        );
    }

    /**
     * Сохраняет список участников {@link Magician} по владельцу {@link Round}
     *
     * @param models Список участников {@link Magician}
     * @param owner  Раунд-владелец участников {@link Round}
     * @return Сохраненный участников
     */
    @Override
    public List<Magician> saveByOwner(List<Magician> models, Round owner) {
        if (models == null) {
            return null;
        }

        // Получаем игру-сущность, в которой произошло событие
        GameEntity gameEntity = roundRepository.getOne(owner.getUuid()).getOwner();

        // Получаем раунд-сущность
        RoundEntity roundEntity = roundMapper.toEntity(owner, gameEntity);

        List<Magician> savedParticipants = new ArrayList<>();
        models.forEach(magician -> {

            Magician participant = magician;
            // Сохраняем мага, если еще не сохранен
            if (magician.getUuid() == null) {
                participant = magicianDao.save(magician);
            }

            Magician savedParticipant = participantsMapper.toModel(
                    participantRepository.save(
                            participantsMapper.toEntity(participant, roundEntity)
                    )
            );
            savedParticipants.add(savedParticipant);
        });

        return savedParticipants;
    }

    /**
     * Сохраняет участника {@link Magician} по владельцу {@link Round}
     *
     * @param model Участник {@link Magician}
     * @param owner Раунд-владелец участников {@link Round}
     * @return Сохраненный участник
     */
    @Override
    public Magician save(Magician model, Round owner) {
        GameEntity gameEntity = roundRepository.getOne(owner.getUuid()).getOwner();
        return participantsMapper.toModel(
                participantRepository.save(
                        participantsMapper.toEntity(
                                model,
                                roundMapper.toEntity(owner, gameEntity)
                        )
                )
        );
    }

    /**
     * Возвращает список всех участников {@link Magician}
     *
     * @return Список всех участников
     */
    @Override
    public List<Magician> findAll() {
        return participantsMapper.entitiesToModels(participantRepository.findAll());
    }

    /**
     * Возвращает участника {@link Magician} по идентификатору {@link UUID}
     *
     * @param uuid Идентификатор участника {@link UUID}
     * @return Участник
     */
    @Override
    public Optional<Magician> findById(UUID uuid) {
        return participantRepository.findById(uuid).map(participantsMapper::toModel);
    }

    /**
     * Удаляет участника {@link Magician} по идентификатору {@link UUID}
     *
     * @param uuid Идентификатор участника {@link UUID}
     */
    @Override
    public void delete(UUID uuid) {
        participantRepository.deleteById(uuid);
    }
}
