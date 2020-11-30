package ru.dilgorp.java.stats.game.table.bom.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.repository.GameRepository;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.dao.DaoWithOwner;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс для тестирования {@link GameDao}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class
})
class GameDaoTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private Mapper<GameEntity, Game> gameMapper;

    @Autowired
    private Dao<Magician, UUID> magicianDao;

    @Autowired
    private DaoWithOwner<Round, UUID, Game> roundDao;

    @Autowired
    private GameGenerator gameGenerator;

    private GameDao gameDao;

    @BeforeEach
    void setUp() {
        gameDao = new GameDao(gameRepository, gameMapper, magicianDao, roundDao);
    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
    }

    /**
     * Проверяем, что возваращается корректный список игр
     */
    @Test
    public void findAllIsCorrect() {
        // given
        List<Game> givenGames = gameGenerator.generate(3);
        gameRepository.saveAll(gameMapper.modelsToEntities(givenGames));

        // when
        List<Game> gettingGames = gameDao.findAll();

        // then
        assertEquals(givenGames, gettingGames);
    }

    /**
     * Проверяем, что игра сохраняется корректно
     */
    @Test
    public void saveIsCorrect() {
        // given
        Game givenGame = gameGenerator.generate(1).get(0);

        // when
        Game gettingGame = gameDao.save(givenGame);
        Optional<GameEntity> gettingOptional = gameRepository.findById(givenGame.getUuid());

        // then
        assertEquals(givenGame, gettingGame);
        assertTrue(gettingOptional.isPresent());
        assertEquals(givenGame, gameMapper.toModel(gettingOptional.get()));
    }

    /**
     * Проверяем, что поиск по идентификатору осуществляется корректно
     */
    @Test
    public void findByIdIsCorrect() {
        // given
        List<Game> givenGames = gameGenerator.generate(3);
        Game givenGame = givenGames.get(0);
        gameRepository.saveAll(gameMapper.modelsToEntities(givenGames));

        // when
        Optional<Game> gettingOptional = gameDao.findById(givenGame.getUuid());
        Optional<Game> emptyOptional = gameDao.findById(UUID.randomUUID());

        // then
        assertTrue(gettingOptional.isPresent());
        assertEquals(givenGame, gettingOptional.get());
        assertEquals(Optional.empty(), emptyOptional);
    }

    /**
     * Проверяем, что удаление по идентификатору осуществляется корректно
     */
    @Test
    public void deleteIsCorrect() {
        // given
        List<Game> givenGames = gameGenerator.generate(3);
        Game givenGame = givenGames.get(0);
        gameRepository.saveAll(gameMapper.modelsToEntities(givenGames));

        // when
        gameDao.delete(givenGame.getUuid());
        Optional<GameEntity> gettingOptional = gameRepository.findById(givenGame.getUuid());

        // then
        assertTrue(gettingOptional.isEmpty());
    }
}