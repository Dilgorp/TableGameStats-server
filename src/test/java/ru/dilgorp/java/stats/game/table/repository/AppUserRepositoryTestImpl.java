package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.stats.game.table.domain.AppUser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class AppUserRepositoryTestImpl implements AppUserRepository {
    private final Map<String, AppUser> users = new LinkedHashMap<>();

    @Override
    public AppUser findByUsername(String username) {
        Optional<AppUser> first = users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
        return first.orElse(null);
    }

    @Override
    public <S extends AppUser> S save(S entity) {
        if (entity.getId() == null) {
            entity.setId("id");
        }
        users.put(entity.getId(), entity);
        return (S) users.get(entity.getId());
    }

    @Override
    public <S extends AppUser> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(e -> users.put(e.getId(), e));
        return (List<S>) List.copyOf(users.values());
    }

    @Override
    public Optional<AppUser> findById(String s) {
        return Optional.ofNullable(users.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<AppUser> findAll() {
        return List.copyOf(users.values());
    }

    @Override
    public Iterable<AppUser> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {
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
    public Page<AppUser> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AppUser> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends AppUser> List<S> insert(Iterable<S> entities) {
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
