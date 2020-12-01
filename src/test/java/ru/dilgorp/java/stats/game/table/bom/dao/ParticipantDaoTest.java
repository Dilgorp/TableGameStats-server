package ru.dilgorp.java.stats.game.table.bom.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.GameEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.RoundParticipantEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Game;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Round;
import ru.dilgorp.java.stats.game.table.bom.repository.ParticipantRepository;
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
 * Класс для тестирования класса {@link ParticipantDao}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        MapperConfig.class,
        DaoConfig.class,
        GeneratorsConfig.class
})
class ParticipantDaoTest {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private MapperWithOwner<RoundParticipantEntity, Magician, RoundEntity> participantsMapper;

    @Autowired
    private MapperWithOwner<RoundEntity, Round, GameEntity> roundMapper;

    @Autowired
    private Dao<Magician, UUID> magicianDao;

    @Autowired
    private GameGenerator gameGenerator;

    @Autowired
    private Mapper<GameEntity, Game> gameMapper;

    private ParticipantDao participantDao;

    @BeforeEach
    void setUp() {
        participantDao = new ParticipantDao(
                participantRepository,
                roundRepository,
                participantsMapper,
                roundMapper,
                magicianDao
        );
    }

    @AfterEach
    void tearDown() {
        participantRepository.deleteAll();
        roundRepository.deleteAll();
    }

    /**
     * Проверяем что возвращается корректный список участников раунда
     */
    @Test
    public void findByOwnerIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);
        Round givenRound = game.getRounds().get(0);

        RoundEntity roundEntity = roundMapper.toEntity(
                givenRound,
                gameMapper.toEntity(game)
        );
        roundRepository.save(roundEntity);

        List<Magician> givenParticipants = givenRound.getParticipants();
        participantRepository.saveAll(
                participantsMapper.modelsToEntities(
                        givenParticipants,
                        roundEntity
                )
        );

        // when
        List<Magician> gettingParticipants = participantDao.findByOwner(givenRound);

        // then
        assertEquals(givenParticipants, gettingParticipants);
    }

    /**
     * Проверяем, что участники сохраняются корректно по владельцу
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
                .forEach(round -> participantDao.saveByOwner(round.getParticipants(), round));

        // then
        game.getRounds().forEach(round -> assertEquals(
                round.getParticipants(),
                participantsMapper.entitiesToModels(
                        participantRepository.findByOwner(
                                roundMapper.toEntity(
                                        round,
                                        gameEntity
                                )
                        )
                )
        ));
    }

    /**
     * Проверяем, что участник сохраняется корректно
     */
    @Test
    public void saveIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);
        GameEntity gameEntity = gameMapper.toEntity(game);
        roundRepository.saveAll(
                roundMapper.modelsToEntities(game.getRounds(), gameEntity)
        );

        // when
        game.getRounds().forEach(round -> round.getParticipants().forEach(participant ->
                participantDao.save(participant, round)
        ));

        // then
        game.getRounds().forEach(round -> round.getParticipants().forEach(participant ->
                assertEquals(
                        participant,
                        participantsMapper.toModel(
                                participantRepository.getOne(participant.getUuid())
                        )
                )
        ));
    }

    /**
     * Проверяем, что возвращается корректный список участников
     */
    @Test
    public void findAllIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);
        Round givenRound = game.getRounds().get(0);

        RoundEntity roundEntity = roundMapper.toEntity(
                givenRound,
                gameMapper.toEntity(game)
        );
        roundRepository.save(roundEntity);

        List<Magician> givenParticipants = givenRound.getParticipants();
        participantRepository.saveAll(
                participantsMapper.modelsToEntities(
                        givenParticipants,
                        roundEntity
                )
        );

        // when
        List<Magician> gettingParticipants = participantDao.findAll();

        // then
        assertEquals(givenParticipants, gettingParticipants);
    }

    /**
     * Проверяем, что возвращается корректный участник по идентификатору
     */
    @Test
    public void findByIdIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);
        Round givenRound = game.getRounds().get(0);

        RoundEntity roundEntity = roundMapper.toEntity(
                givenRound,
                gameMapper.toEntity(game)
        );
        roundRepository.save(roundEntity);

        List<Magician> givenParticipants = givenRound.getParticipants();
        participantRepository.saveAll(
                participantsMapper.modelsToEntities(
                        givenParticipants,
                        roundEntity
                )
        );
        Magician givenParticipant = givenParticipants.get(0);

        // when
        Optional<Magician> gettingOptional = participantDao.findById(givenParticipant.getUuid());
        Optional<Magician> emptyOptional = participantDao.findById(UUID.randomUUID());

        // then
        assertTrue(gettingOptional.isPresent());
        assertEquals(givenParticipant, gettingOptional.get());
        assertEquals(Optional.empty(), emptyOptional);
    }

    /**
     * Проверяем, что участник удаляется корректно
     */
    @Test
    public void deleteIsCorrect() {
        // given
        Game game = gameGenerator.generate(1).get(0);
        Round givenRound = game.getRounds().get(0);

        RoundEntity roundEntity = roundMapper.toEntity(
                givenRound,
                gameMapper.toEntity(game)
        );
        roundRepository.save(roundEntity);

        List<Magician> givenParticipants = givenRound.getParticipants();
        participantRepository.saveAll(
                participantsMapper.modelsToEntities(
                        givenParticipants,
                        roundEntity
                )
        );
        Magician givenParticipant = givenParticipants.get(0);

        // when
        participantDao.delete(givenParticipant.getUuid());
        Optional<Magician> gettingOptional = participantRepository
                .findById(givenParticipant.getUuid()).map(participantsMapper::toModel);

        // then
        assertEquals(Optional.empty(), gettingOptional);
    }
}