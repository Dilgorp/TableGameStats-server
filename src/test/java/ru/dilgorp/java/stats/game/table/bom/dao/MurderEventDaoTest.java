package ru.dilgorp.java.stats.game.table.bom.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.event.MurderEventEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.domain.model.event.MurderEvent;
import ru.dilgorp.java.stats.game.table.bom.repository.MurderEventRepository;
import ru.dilgorp.java.stats.game.table.bom.repository.RoundRepository;
import ru.dilgorp.java.stats.game.table.config.*;
import ru.dilgorp.java.stats.game.table.dao.Dao;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.domain.mapper.MapperWithOwner;
import ru.dilgorp.java.stats.game.table.generator.GameGenerator;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Класс для тестирования класса {@link MurderEventDao}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class
})
class MurderEventDaoTest {

    @Autowired
    private MurderEventRepository murderEventRepository;
    @Autowired
    private RoundRepository roundRepository;
    @Autowired
    private MapperWithOwner<MurderEventEntity, MurderEvent, RoundEntity> murderEventMapper;
    @Autowired
    private MapperWithOwner<RoundEntity, Round, GameEntity> roundMapper;
    @Autowired
    private Mapper<GameEntity, Game> gameMapper;
    @Autowired
    private Dao<Magician, UUID> magicianDao;
    @Autowired
    private GameGenerator gameGenerator;

    private MurderEventDao murderEventDao;

    @BeforeEach
    void setUp() {
        murderEventDao = new MurderEventDao(
                murderEventRepository,
                roundRepository,
                murderEventMapper,
                roundMapper,
                magicianDao
        );
    }

    @AfterEach
    void tearDown() {
        murderEventRepository.deleteAll();
        roundRepository.deleteAll();
    }

    /**
     * Проверяем, что, при поиске по владельцу, возвращается корректный список событий
     */
    @Test
    public void findByOwnerIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);

        GameEntity gameEntity = gameMapper.toEntity(game);
        roundRepository.saveAll(
                roundMapper.modelsToEntities(game.getRounds(), gameEntity)
        );
        game.getRounds().forEach(round -> murderEventRepository.saveAll(
                murderEventMapper.modelsToEntities(
                        round.getMurders(),
                        roundMapper.toEntity(round, gameEntity)
                )
        ));

        Map<Round, List<MurderEvent>> byOwner = new HashMap<>();

        // when
        game.getRounds().forEach(round -> byOwner.put(round, murderEventDao.findByOwner(round)));

        // then
        byOwner.forEach((round, murderEvents) -> assertEquals(round.getMurders(), murderEvents));
    }

    /**
     * Проверяем, что список событий сохраняется корректно
     */
    @Test
    public void saveByOwnerIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);
        GameEntity gameEntity = gameMapper.toEntity(game);
        roundRepository.saveAll(
                roundMapper.modelsToEntities(game.getRounds(), gameEntity)
        );

        // when
        game.getRounds()
                .forEach(round -> murderEventDao.saveByOwner(round.getMurders(), round));

        // then
        game.getRounds().forEach(round -> assertEquals(
                round.getMurders(),
                murderEventMapper.entitiesToModels(
                        murderEventRepository.findByOwner(
                                roundMapper.toEntity(round, gameEntity)
                        )
                )
        ));
    }

    /**
     * Проверяем, что возвращается корректный список событий
     */
    @Test
    public void findAllIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);
        List<MurderEvent> givenMurdersEvent = new ArrayList<>();
        game.getRounds().forEach(round -> round.getMurders().forEach(murderEvent -> {
            murderEventRepository.save(
                    murderEventMapper.toEntity(
                            murderEvent,
                            roundMapper.toEntity(
                                    round,
                                    gameMapper.toEntity(game)
                            )
                    )
            );
            givenMurdersEvent.add(murderEvent);
        }));

        // when
        List<MurderEvent> gettingMurdersEvent = murderEventDao.findAll();

        // then
        assertEquals(givenMurdersEvent, gettingMurdersEvent);
    }

    /**
     * Проверяем, что возвращается корректный ответ, при поиске по идентификатору
     */
    @Test
    public void findByIdIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);
        MurderEvent givenEvent = game.getRounds().get(0).getMurders().get(0);
        murderEventRepository.save(
                murderEventMapper.toEntity(
                        givenEvent,
                        roundMapper.toEntity(
                                game.getRounds().get(0),
                                gameMapper.toEntity(game)
                        )
                )
        );

        // when
        Optional<MurderEvent> gettingOptional = murderEventDao.findById(givenEvent.getUuid());
        Optional<MurderEvent> emptyOptional = murderEventDao.findById(UUID.randomUUID());

        // then
        assertTrue(gettingOptional.isPresent());
        assertEquals(givenEvent, gettingOptional.get());
        assertEquals(Optional.empty(), emptyOptional);
    }

    /**
     * Проверяем, что удаление по идентификатору проходит корректно
     */
    @Test
    public void deleteIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);
        MurderEvent givenEvent = game.getRounds().get(0).getMurders().get(0);
        murderEventRepository.save(
                murderEventMapper.toEntity(
                        givenEvent,
                        roundMapper.toEntity(
                                game.getRounds().get(0),
                                gameMapper.toEntity(game)
                        )
                )
        );

        // when
        murderEventDao.delete(givenEvent.getUuid());

        // then
        assertEquals(Optional.empty(), murderEventRepository.findById(givenEvent.getUuid()));
    }
}