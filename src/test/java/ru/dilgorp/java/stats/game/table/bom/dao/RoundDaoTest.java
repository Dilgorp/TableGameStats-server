package ru.dilgorp.java.stats.game.table.bom.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс для тестирования {@link RoundDao}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class
})
class RoundDaoTest {

    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private MapperWithOwner<RoundEntity, Round, GameEntity> roundMapper;
    @Autowired
    private Mapper<GameEntity, Game> gameMapper;
    @Autowired
    private Dao<Magician, UUID> magicianDao;
    @Autowired
    private GameGenerator gameGenerator;
    @Autowired
    private MurderEventDao murderEventDao;

    private RoundDao roundDao;

    @BeforeEach
    void setUp() {
        roundDao = new RoundDao(
                roundRepository,
                gameMapper,
                roundMapper,
                magicianDao,
                murderEventDao
        );
    }

    @AfterEach
    void tearDown() {
        roundRepository.deleteAll();
    }

    /**
     * Проверяем, что, при поиске по владельцу, возвращается корректный список раундов
     */
    @Test
    public void findByOwnerIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);

        GameEntity gameEntity = gameMapper.toEntity(game);
        roundRepository.saveAll(
                roundMapper.modelsToEntities(game.getRounds(), gameEntity)
        );

        // when
        List<Round> gettingRounds = roundDao.findByOwner(game);

        // then
        assertEquals(game.getRounds(), gettingRounds);
    }

    /**
     * Проверяем, что список раундов сохраняется корректно
     */
    @Test
    public void saveByOwnerIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);

        // when
        roundDao.saveByOwner(game.getRounds(), game);
        List<Round> gettingRounds = roundMapper.entitiesToModels(roundRepository.findAll());

        // then
        assertEquals(game.getRounds(), gettingRounds);
    }

    /**
     * Проверяем, что возвращается корректный список раундов
     */
    @Test
    public void findAllIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);
        roundRepository.saveAll(
                roundMapper.modelsToEntities(
                        game.getRounds(), gameMapper.toEntity(game)
                )
        );

        // when
        List<Round> gettingRounds = roundDao.findAll();

        // then
        assertEquals(game.getRounds(), gettingRounds);
    }

    /**
     * Проверяем, что раунды сохраняются корректно
     */
    @Test
    public void saveIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);

        // when
        game.getRounds().forEach(round -> roundDao.save(round, game));
        List<Round> gettingRounds = roundMapper.entitiesToModels(roundRepository.findAll());

        // then
        assertEquals(game.getRounds(), gettingRounds);
    }

    /**
     * Проверяем, что возвращается корректный ответ по идентификатору
     */
    @Test
    public void findByIdIsCorrect(){
        // given
        Game game = gameGenerator.generate(1).get(0);
        roundRepository.saveAll(
                roundMapper.modelsToEntities(
                        game.getRounds(), gameMapper.toEntity(game)
                )
        );

        // when
        Optional<Round> gettingOptional = roundDao.findById(game.getRounds().get(0).getUuid());
        Optional<Round> emptyOptional = roundDao.findById(UUID.randomUUID());

        // then
        assertTrue(gettingOptional.isPresent());
        assertEquals(game.getRounds().get(0), gettingOptional.get());
        assertEquals(Optional.empty(), emptyOptional);
    }

    @Test
    public void deleteIsCorrect(){
        // given
        Game game = gameGenerator.generate(1).get(0);
        roundRepository.saveAll(
                roundMapper.modelsToEntities(
                        game.getRounds(), gameMapper.toEntity(game)
                )
        );
        UUID givenUuid = game.getRounds().get(0).getUuid();

        // when
        roundDao.delete(givenUuid);

        // then
        assertEquals(Optional.empty(), roundRepository.findById(givenUuid));
    }
}