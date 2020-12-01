package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundParticipantEntity;
import ru.dilgorp.java.stats.game.table.bom.repository.ParticipantRepository;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class ParticipantRepositoryTestImpl implements ParticipantRepository {
    private final Map<UUID, RoundParticipantEntity> participants = new LinkedHashMap<>();

    @Override
    public List<RoundParticipantEntity> findByOwner(RoundEntity owner) {
        return participants.values().stream()
                .filter(e -> e.getOwner().getUuid().equals(owner.getUuid()))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoundParticipantEntity> findAll() {
        return List.copyOf(participants.values());
    }

    @Override
    public List<RoundParticipantEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<RoundParticipantEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<RoundParticipantEntity> findAllById(Iterable<UUID> uuids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {
        participants.remove(uuid);
    }

    @Override
    public void delete(RoundParticipantEntity entity) {
        participants.remove(entity.getUuid());
    }

    @Override
    public void deleteAll(Iterable<? extends RoundParticipantEntity> entities) {

    }

    @Override
    public void deleteAll() {
        participants.clear();
    }

    @Override
    public <S extends RoundParticipantEntity> S save(S entity) {
        participants.put(entity.getUuid(), entity);
        return (S) participants.get(entity.getUuid());
    }

    @Override
    public <S extends RoundParticipantEntity> List<S> saveAll(Iterable<S> entities) {
        List<S> savedEntities = new ArrayList<>();
        entities.forEach(e -> {
            participants.put(e.getUuid(), e);
            savedEntities.add(e);
        });
        return savedEntities;
    }

    @Override
    public Optional<RoundParticipantEntity> findById(UUID uuid) {
        return Optional.ofNullable(participants.get(uuid));
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends RoundParticipantEntity> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<RoundParticipantEntity> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public RoundParticipantEntity getOne(UUID uuid) {
        return participants.get(uuid);
    }

    @Override
    public <S extends RoundParticipantEntity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends RoundParticipantEntity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends RoundParticipantEntity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends RoundParticipantEntity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends RoundParticipantEntity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends RoundParticipantEntity> boolean exists(Example<S> example) {
        return false;
    }
}
