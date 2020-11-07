package ru.dilgorp.java.stats.game.table.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.dilgorp.java.stats.game.table.domain.AppUser;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {
}
