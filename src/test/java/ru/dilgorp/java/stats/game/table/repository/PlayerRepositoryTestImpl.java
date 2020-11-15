package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.dilgorp.java.stats.game.table.domain.Player;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class PlayerRepositoryTestImpl implements PlayerRepository{
    private Map<String, Player> players = new LinkedHashMap<>();

    @Override
    public Player findByName(String name) {
        Optional<Player> first = players.values().stream()
                .filter(player -> player.getName().equals(name))
                .findFirst();
        return first.orElse(null);
    }

    @Override
    public <S extends Player> S save(S entity) {
        if(entity.getId() == null){
            entity.setId("id");
        }

        players.put(entity.getId(), entity);
        return (S) players.get(entity.getId());
    }

    @Override
    public <S extends Player> List<S> saveAll(Iterable<S> entities) {
        entities.forEach(s -> players.put(s.getId(), s));
        return (List<S>) List.copyOf(players.values());
    }

    @Override
    public Optional<Player> findById(String s) {
        return Optional.ofNullable(players.get(s));
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Player> findAll() {
        return List.copyOf(players.values());
    }

    @Override
    public Iterable<Player> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Player entity) {
        players.remove(entity.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Player> entities) {

    }

    @Override
    public void deleteAll() {
        players.clear();
    }

    @Override
    public List<Player> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Player> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Player> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Player> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Player> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Player> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Player> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Player> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Player> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Player> boolean exists(Example<S> example) {
        return false;
    }
}
