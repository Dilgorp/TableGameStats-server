package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.repository.MagicianRepository;

import java.util.*;

@SuppressWarnings("unchecked")
public class MagicianRepositoryTestImpl implements MagicianRepository {

    private final Map<UUID, MagicianEntity> magicians = new LinkedHashMap<>();

    @Override
    public List<MagicianEntity> findAll() {
        return List.copyOf(magicians.values());
    }

    @Override
    public List<MagicianEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<MagicianEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<MagicianEntity> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {
        magicians.remove(uuid);
    }

    @Override
    public void delete(MagicianEntity entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends MagicianEntity> entities) {

    }

    @Override
    public void deleteAll() {
        magicians.clear();
    }

    @Override
    public <S extends MagicianEntity> S save(S entity) {
        return (S) magicians.put(entity.getUuid(), entity);
    }

    @Override
    public <S extends MagicianEntity> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(s -> magicians.put(s.getUuid(), s));

        return (List<S>) List.copyOf(magicians.values());
    }

    @Override
    public Optional<MagicianEntity> findById(UUID uuid) {
        return Optional.ofNullable(magicians.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends MagicianEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<MagicianEntity> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public MagicianEntity getOne(UUID uuid) {
        return magicians.get(uuid);
    }

    @Override
    public <S extends MagicianEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends MagicianEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends MagicianEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends MagicianEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends MagicianEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends MagicianEntity> boolean exists(Example<S> example) {
        return false;
    }
}
