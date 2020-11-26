package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class RoundRepositoryTestImpl implements RoundRepository {
    private final Map<UUID, RoundEntity> rounds = new LinkedHashMap<>();

    @Override
    public List<RoundEntity> findByOwner(GameEntity owner) {
        return rounds.values().stream()
                .filter(e -> e.getOwner().getUuid().equals(owner.getUuid()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoundEntity> findAll() {
        return List.copyOf(rounds.values());
    }

    @Override
    public List<RoundEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<RoundEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<RoundEntity> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {
        rounds.remove(uuid);
    }

    @Override
    public void delete(RoundEntity entity) {
        rounds.remove(entity.getUuid());
    }

    @Override
    public void deleteAll(Iterable<? extends RoundEntity> entities) {

    }

    @Override
    public void deleteAll() {
        rounds.clear();
    }

    @Override
    public <S extends RoundEntity> S save(S entity) {
        return (S) rounds.put(entity.getUuid(), entity);
    }

    @Override
    public <S extends RoundEntity> List<S> saveAll(Iterable<S> entities) {
        List<RoundEntity> entityList = new ArrayList<>();
        entities.forEach(s -> entityList.add(rounds.put(s.getUuid(), s)));
        return (List<S>) entityList;
    }

    @Override
    public Optional<RoundEntity> findById(UUID uuid) {
        return Optional.ofNullable(rounds.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends RoundEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<RoundEntity> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public RoundEntity getOne(UUID uuid) {
        return rounds.get(uuid);
    }

    @Override
    public <S extends RoundEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends RoundEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends RoundEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends RoundEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends RoundEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends RoundEntity> boolean exists(Example<S> example) {
        return false;
    }
}
