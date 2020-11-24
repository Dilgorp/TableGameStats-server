package ru.dilgorp.java.stats.game.table.bom.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.repository.MagicianRepository;
import ru.dilgorp.java.stats.game.table.config.GeneratorsConfig;
import ru.dilgorp.java.stats.game.table.config.MapperConfig;
import ru.dilgorp.java.stats.game.table.config.RepositoriesConfig;
import ru.dilgorp.java.stats.game.table.config.TestConfig;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;
import ru.dilgorp.java.stats.game.table.generator.MagicianGenerator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Класс для тестирования {@link MagicianDao}
 */
@SpringJUnitConfig({
        TestConfig.class,
        RepositoriesConfig.class,
        GeneratorsConfig.class,
        MapperConfig.class
})
class MagicianDaoTest {

    @Autowired
    private MagicianRepository magicianRepository;

    @Autowired
    private Mapper<MagicianEntity, Magician> magicianMapper;

    @Autowired
    private MagicianGenerator magicianGenerator;

    private MagicianDao magicianDao;

    @BeforeEach
    void setUp() {
        magicianDao = new MagicianDao(magicianRepository, magicianMapper);
    }

    @AfterEach
    void tearDown() {
        magicianRepository.deleteAll();
    }

    /**
     * Проверяем, что возвращается корректный список магов
     */
    @Test
    public void findAllIsCorrect() {
        // given
        List<Magician> magicians = magicianGenerator.generate(3);
        magicianRepository.saveAll(magicianMapper.modelsToEntities(magicians));

        // when
        List<Magician> all = magicianDao.findAll();

        // then
        assertEquals(magicians, all);
    }

    /**
     * Проверяем, что маг сохраняется корректно
     */
    @Test
    public void saveIsCorrect() {
        // given
        Magician magician = magicianGenerator.generate(1).get(0);

        // when
        magicianDao.save(magician);

        // then
        assertEquals(magician, magicianMapper.toModel(magicianRepository.getOne(magician.getUuid())));
    }

    /**
     * Проверяем, что возвращается корректный {@link Optional} по идентификатору
     */
    @Test
    public void findByIdIsCorrect() {
        // given
        Magician magician = magicianGenerator.generate(1).get(0);
        Optional<Magician> magicianOptional = Optional.ofNullable(magician);
        magicianRepository.save(magicianMapper.toEntity(magician));

        // when
        assert magician != null;
        Optional<Magician> byId = magicianDao.findById(magician.getUuid());

        // then
        assertEquals(magicianOptional, byId);
        assertEquals(Optional.empty(), magicianDao.findById(UUID.randomUUID()));
    }

    /**
     * Проверяем, что маг удаляется корректно
     */
    @Test
    public void deleteIsCorrect() {
        // given
        List<Magician> magicians = magicianGenerator.generate(2);
        magicianRepository.saveAll(magicianMapper.modelsToEntities(magicians));

        // when
        magicianDao.delete(magicians.get(0).getUuid());
        Magician gettingMagician = magicianMapper.toModel(
                magicianRepository.findAll().get(0)
        );

        // then
        assertEquals(1, magicianRepository.findAll().size());
        assertEquals(magicians.get(1), gettingMagician);
        assertNotEquals(magicians.get(0), gettingMagician);
    }
}