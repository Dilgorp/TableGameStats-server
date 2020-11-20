package ru.dilgorp.java.stats.game.table.bom.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.repository.MagicianRepository;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Обеспечивает работу с данными базы по модели {@link Magician}
 */
@Service
@RequiredArgsConstructor
public class MagicianDao implements Dao<Magician, UUID> {

    private final MagicianRepository magicianRepository;
    private final Mapper<MagicianEntity, Magician> magicianMapper;

    /**
     * Возвращает список всех магов {@link Magician}
     *
     * @return Список всех магов {@link Magician}
     */
    @Override
    public List<Magician> findAll() {
        return List.copyOf(
                magicianMapper.entitiesToModels(
                        magicianRepository.findAll()
                )
        );
    }

    /**
     * Сохраняет мага {@link Magician} в базу данных
     *
     * @param model Маг {@link Magician}
     * @return Маг {@link Magician}
     */
    @Override
    public Magician save(Magician model) {
        return magicianMapper.toModel(
                magicianRepository.save(
                        magicianMapper.toEntity(model)
                )
        );
    }

    /**
     * Возвращает мага {@link Magician} по идентификатору {@link UUID}
     *
     * @param uuid Идентификатор {@link UUID}
     * @return Маг {@link Magician}
     */
    @Override
    public Optional<Magician> findById(UUID uuid) {
        return magicianRepository.findById(uuid).map(magicianMapper::toModel);
    }

    /**
     * Удаляет мага {@link Magician} по идентификатору {@link UUID}
     *
     * @param uuid Идентификатор {@link UUID}
     */
    @Override
    public void delete(UUID uuid) {
        magicianRepository.deleteById(uuid);
    }
}
