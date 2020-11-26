package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.event.MurderEventEntity;
import ru.dilgorp.java.stats.game.table.bom.repository.MurderEventRepository;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class MurderEventRepositoryTestImpl implements MurderEventRepository {
    private final Map<UUID, MurderEventEntity> events = new LinkedHashMap<>();

    @Override
    public List<MurderEventEntity> findByOwner(RoundEntity owner) {
        return events.values().stream()
                .filter(e -> e.getOwner().getUuid().equals(owner.getUuid()))
                .collect(Collectors.toList());
    }

    @Override
    public List<MurderEventEntity> findAll() {
        return List.copyOf(events.values());
    }

    @Override
    public List<MurderEventEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<MurderEventEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<MurderEventEntity> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {
        events.remove(uuid);
    }

    @Override
    public void delete(MurderEventEntity entity) {
        events.remove(entity.getUuid());
    }

    @Override
    public void deleteAll(Iterable<? extends MurderEventEntity> entities) {

    }

    @Override
    public void deleteAll() {
        events.clear();
    }

    @Override
    public <S extends MurderEventEntity> S save(S entity) {
        return (S) events.put(entity.getUuid(), entity);
    }

    @Override
    public <S extends MurderEventEntity> List<S> saveAll(Iterable<S> entities) {
        List<MurderEventEntity> entityList = new ArrayList<>();
        entities.forEach(s -> entityList.add(events.put(s.getUuid(), s)));
        return (List<S>) entityList;
    }

    @Override
    public Optional<MurderEventEntity> findById(UUID uuid) {
        return Optional.ofNullable(events.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends MurderEventEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<MurderEventEntity> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MurderEventEntity getOne(UUID uuid) {
        return events.get(uuid);
    }

    @Override
    public <S extends MurderEventEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends MurderEventEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends MurderEventEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends MurderEventEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends MurderEventEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MurderEventEntity> boolean exists(Example<S> example) {
        return false;
    }
}
