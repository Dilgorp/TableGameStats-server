package ru.dilgorp.java.stats.game.table.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dilgorp.java.stats.game.table.bom.dao.MagicianDao;
import ru.dilgorp.java.stats.game.table.bom.domain.entity.MagicianEntity;
import ru.dilgorp.java.stats.game.table.bom.domain.model.Magician;
import ru.dilgorp.java.stats.game.table.bom.repository.MagicianRepository;
import ru.dilgorp.java.stats.game.table.domain.mapper.Mapper;

@Configuration
public class DaoConfig {

    @Autowired
    private MagicianRepository magicianRepository;

    @Autowired
    private Mapper<MagicianEntity, Magician> magicianMapper;

    @Bean
    public MagicianDao magicianDao(){
        return new MagicianDao(magicianRepository, magicianMapper);
    }
}
