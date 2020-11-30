package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;

import java.util.*;

@SuppressWarnings("unchecked")
public class GameRepositoryTestImpl implements GameRepository {

    private Map<UUID, GameEntity> games = new LinkedHashMap<>();

    @Override
    public List<GameEntity> findAll() {
        return List.copyOf(games.values());
    }

    @Override
    public List<GameEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<GameEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<GameEntity> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {
        games.remove(uuid);
    }

    @Override
    public void delete(GameEntity entity) {
        games.remove(entity.getUuid());
    }

    @Override
    public void deleteAll(Iterable<? extends GameEntity> entities) {

    }

    @Override
    public void deleteAll() {
        games.clear();
    }

    @Override
    public <S extends GameEntity> S save(S entity) {
        games.put(entity.getUuid(), entity);
        return (S) games.get(entity.getUuid());
    }

    @Override
    public <S extends GameEntity> List<S> saveAll(Iterable<S> entities) {
        List<S> savedEntities = new ArrayList<>();
        entities.forEach(e -> {
            games.put(e.getUuid(), e);
            savedEntities.add(e);
        });
        return savedEntities;
    }

    @Override
    public Optional<GameEntity> findById(UUID uuid) {
        return Optional.ofNullable(games.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends GameEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<GameEntity> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public GameEntity getOne(UUID uuid) {
        return games.get(uuid);
    }

    @Override
    public <S extends GameEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends GameEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends GameEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends GameEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends GameEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends GameEntity> boolean exists(Example<S> example) {
        return false;
    }
}
