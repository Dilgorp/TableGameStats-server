package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.stats.game.table.domain.AppUser;

import java.util.*;

@SuppressWarnings("unchecked")
public class AppUserRepositoryTestImpl implements AppUserRepository {
    private final Map<UUID, AppUser> users = new LinkedHashMap<>();

    @Override
    public AppUser findByUsername(String username) {
        Optional<AppUser> first = users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        return first.orElse(null);
    }

    @Override
    public <S extends AppUser> S save(S entity) {
        if (entity.getUuid() == null) {
            entity.setUuid(UUID.randomUUID());
        }
        users.put(entity.getUuid(), entity);
        return (S) users.get(entity.getUuid());
    }

    @Override
    public <S extends AppUser> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(e -> users.put(e.getUuid(), e));
        return (List<S>) List.copyOf(users.values());
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AppUser> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<AppUser> entities) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AppUser getOne(UUID s) {
        return null;
    }

    @Override
    public Optional<AppUser> findById(UUID s) {
        return Optional.ofNullable(users.get(s));
    }

    @Override
    public boolean existsById(UUID s) {
        return false;
    }

    @Override
    public List<AppUser> findAll() {
        return List.copyOf(users.values());
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID s) {
        users.remove(s);
    }

    @Override
    public void delete(AppUser entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends AppUser> entities) {

    }

    @Override
    public void deleteAll() {
        users.clear();
    }

    @Override
    public List<AppUser> findAll(Sort sort) {
        return null;
    }

    @Override
    public List<AppUser> findAllById(Iterable<UUID> strings) {
        return null;
    }

    @Override
    public Page<AppUser> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AppUser> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AppUser> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends AppUser> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends AppUser> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AppUser> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AppUser> boolean exists(Example<S> example) {
        return false;
    }
}
